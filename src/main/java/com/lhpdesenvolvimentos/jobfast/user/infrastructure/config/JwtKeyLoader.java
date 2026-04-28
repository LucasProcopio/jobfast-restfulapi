package com.lhpdesenvolvimentos.jobfast.user.infrastructure.config;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class JwtKeyLoader {

    private final ResourceLoader resourceLoader;

    public JwtKeyLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private static String cleanPem(String pem) {
        return pem.replaceAll("-----BEGIN (?:.*)-----", "")
                .replaceAll("-----END (?:.*)-----", "")
                .replaceAll("\\s", "");
    }

    private Resource resolveLocation(String rawPath) {
        if (rawPath == null || rawPath.isBlank())
            return null;

        String path = rawPath.trim();

        // expand ~ to user home
        if (path.startsWith("~")) {
            path = System.getProperty("user.home") + path.substring(1);
        }

        // if user provided an absolute FS path without scheme, prefix file:
        if (!(path.startsWith("classpath:") || path.startsWith("file:") || path.startsWith("http:")
                || path.startsWith("ftp:"))) {
            File f = new File(path);
            if (f.isAbsolute()) {
                path = "file:" + path;
            } else {
                // relative path -> also treat as file:
                path = "file:" + f.getAbsolutePath();
            }
        }

        return resourceLoader.getResource(path);
    }

    @Bean
    public PrivateKey jwtPrivateKey(
            @Value("${jwt.private-key-path:}") String keyPath,
            @Value("${jwt.private-key-base64:}") String keyBase64) throws Exception {

        byte[] der;
        if (keyBase64 != null && !keyBase64.isBlank()) {
            der = Base64.getDecoder().decode(keyBase64);
        } else {
            Resource res = resolveLocation(keyPath);
            if (res == null || !res.exists()) {
                throw new IllegalStateException(
                        "Private key not found. Configure jwt.private-key-path (use file:/absolute/path or classpath:) or jwt.private-key-base64");
            }
            String pem = new String(res.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            String cleaned = cleanPem(pem);
            der = Base64.getDecoder().decode(cleaned);
        }

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(der);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    @Bean
    public PublicKey jwtPublicKey(
            @Value("${jwt.public-key-path:}") String keyPath,
            @Value("${jwt.public-key-base64:}") String keyBase64) throws Exception {

        byte[] der;
        if (keyBase64 != null && !keyBase64.isBlank()) {
            der = Base64.getDecoder().decode(keyBase64);
        } else {
            Resource res = resolveLocation(keyPath);
            if (res == null || !res.exists()) {
                throw new IllegalStateException(
                        "Public key not found. Configure jwt.public-key-path (use file:/absolute/path or classpath:) or jwt.public-key-base64");
            }
            String pem = new String(res.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            String cleaned = cleanPem(pem);
            der = Base64.getDecoder().decode(cleaned);
        }

        X509EncodedKeySpec spec = new X509EncodedKeySpec(der);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }
}