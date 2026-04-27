package com.lhpdesenvolvimentos.jobfast.user.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SmptEmailService implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${app.frontend.base-url}")
    private String frontEndBaseUrl;

    @Async
    @Override
    public void sendVerificationEmail(String to, String token) {
        String link = frontEndBaseUrl + "/verify?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("JobFast - Verificação de Email");
        message.setText("Olá,\n\nPor favor, clique no link abaixo para verificar seu email:\n" + link + "\n\nObrigado,\nEquipe JobFast");
        mailSender.send(message);
    }
    
}
