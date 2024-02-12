package com.example.fishop.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendHtmlEmail(String subject, String htmltext,String username, String recipient) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("fishop@example.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, recipient);
        message.setSubject(subject);

        String htmlContent = "<h1> Dear "+username+"</h1>" +
                "<p>We want to notify you about:" + subject +
                    htmltext+
                "</p>";
        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);
    }
}
