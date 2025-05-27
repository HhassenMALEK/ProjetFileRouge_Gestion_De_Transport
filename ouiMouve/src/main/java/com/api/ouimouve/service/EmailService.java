package com.api.ouimouve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;

    public void sendAlert(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("✅ Email d'alerte envoyé !");
    }


    public void uneFonctionQuelconque() {
        // Pour envoyer un email d'alerte
        this.sendAlert(
                "h_hassen.malek@hotmail.com",
                "Sujet de l'email",
                "Contenu de l'email d'alerte"
        );
    }
}
