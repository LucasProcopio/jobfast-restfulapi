package com.lhpdesenvolvimentos.jobfast.user.application.service;

public interface EmailService {
    void sendVerificationEmail(String to, String token);
}
