/*
package com.crypto.service;

import com.crypto.model.CryptoPrice;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPriceAlertEmail(String email, CryptoPrice crypto, double threshold) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("alerts@cryptocollector.com");
        message.setTo(email);
        message.setSubject("Crypto Price Alert: " + crypto.getSymbol());
        message.setText(String.format(
                "Alert for %s:\n" +
                        "Current Price: $%.2f\n" +
                        "Threshold: $%.2f",
                crypto.getName(), crypto.getPrice(), threshold
        ));

        mailSender.send(message);
    }
}*/
