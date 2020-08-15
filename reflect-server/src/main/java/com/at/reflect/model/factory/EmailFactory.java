package com.at.reflect.model.factory;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;

public class EmailFactory {
    @Value("${spring.email.username}")
    private String username;
    @Value("${spring.email.password}")
    private String password;

    public void sendEmail(final String toAddress) {
        sendEmail(toAddress, "Test email Subject", "Test email body");
    }

    public void sendEmail(final String toAddress, String emailSubject, String emailBody) {

        Session session = fetchSession(username, password);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(emailSubject);
            message.setText(emailBody);
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Session fetchSession(String username, String password) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS
        return Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
