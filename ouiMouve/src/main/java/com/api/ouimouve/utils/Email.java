package com.api.ouimouve.utils;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service responsable de l'envoi d'emails texte simples.
 */
@Service
@RequiredArgsConstructor
public class Email {
    // Service de Spring Boot qui envoie réellement les emails via SMTP
    private final JavaMailSender mailSender;

    @Value("${spring.mail.from:noreply@ouimouve.com}") // Valeur par défaut
    private String emailFrom;
    // Logger pour journaliser les actions de ce service (ici : quand un mail est envoyé)
    private static final Logger logger = LoggerFactory.getLogger(Email.class);

    /**
     * Envoie un email simple au format texte.
     *
     * @param to destinataire
     * @param subject sujet de l'email
     * @param body contenu de l'email
     */
    public void sendAlert(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        logger.info("Email envoyé à {} ",  to);
    }
}