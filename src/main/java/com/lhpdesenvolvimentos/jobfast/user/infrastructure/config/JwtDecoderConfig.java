// Java
// Opção A: criar JwtDecoder a partir do caminho do PEM (usa jwt.public-key-path já presente)
package com.lhpdesenvolvimentos.jobfast.user.infrastructure.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtDecoderConfig {

    @Bean
    public JwtDecoder jwtDecoder(@Value("${jwt.public-key-path}") String publicKeyPath) {
        try {
            String pem = Files.readString(Path.of(publicKeyPath));
            pem = pem.replace("-----BEGIN PUBLIC KEY-----", "")
                     .replace("-----END PUBLIC KEY-----", "")
                     .replaceAll("\\s+", "");
            byte[] der = Base64.getDecoder().decode(pem);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(der);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(spec);
            return NimbusJwtDecoder.withPublicKey(pubKey).build();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to create JwtDecoder from PEM: " + ex.getMessage(), ex);
        }
    }
}