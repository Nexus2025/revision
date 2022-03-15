package com.revision.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class MailService {

    public static void sendEmailToAdmin(String userLogin) {
        Transport transport = null;
        InputStream inputStream = null;
        Date date = new Date();

        try {
            inputStream = Encryptor.class.getResourceAsStream("/mail.properties");
            Properties mailDataProperties = new Properties();
            mailDataProperties.load(inputStream);
            String senderEmail = mailDataProperties.getProperty("sender.email");
            String senderPassword = mailDataProperties.getProperty("sender.password");
            String recipientEmail = mailDataProperties.getProperty("recipient.email");

            Properties mailSettingsProperties = System.getProperties();
            mailSettingsProperties.setProperty("mail.smtps.host", "smtp.yandex.com");
            mailSettingsProperties.setProperty("mail.transport.protocol", "smtps");
            mailSettingsProperties.setProperty("mail.smtps.auth", "true");
            mailSettingsProperties.setProperty("mail.smtp.starttls.enabl", "true");
            mailSettingsProperties.setProperty("mail.smtp.user", senderEmail);
            mailSettingsProperties.setProperty("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(mailSettingsProperties);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Revision App: New user was added");
            message.setText("User login: " + userLogin + "\nRegistration date: " + date);

            transport = session.getTransport();
            transport.connect(senderEmail, senderPassword);
            transport.sendMessage(message, message.getAllRecipients());

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (transport != null) {
                    transport.close();
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MailService(){}
}
