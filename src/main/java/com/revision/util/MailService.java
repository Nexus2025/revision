package com.revision.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailService {

        public static void sendEmailToAdmin (String userLogin) {
                Transport transport = null;

                Date date = new Date();

                try {
                        Properties properties = System.getProperties();
                        properties.setProperty("mail.smtps.host", "smtp.yandex.com");
                        properties.setProperty("mail.transport.protocol", "smtps");
                        properties.setProperty("mail.smtps.auth", "true");
                        properties.setProperty("mail.smtp.starttls.enabl", "true");
                        properties.setProperty("mail.smtp.user", "pr0v0dnik2014@yandex.ru");
                        properties.setProperty("mail.smtp.port", "465");


                        Session session = Session.getDefaultInstance(properties);
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("pr0v0dnik2014@yandex.ru"));
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress("roman70rus@gmail.com"));
                        message.setSubject("Revision App: New user added");
                        message.setText("Login: " + userLogin + " / date: " + date);

                        transport = session.getTransport();
                        transport.connect("pr0v0dnik2014", "123876rus");
                        transport.sendMessage(message, message.getAllRecipients());

                } catch (MessagingException e) {
                        e.printStackTrace();
                } finally {
                        try {
                                if (transport != null) {
                                        transport.close();
                                }

                        } catch (MessagingException e) {
                                e.printStackTrace();
                        }
                }

        }
}
