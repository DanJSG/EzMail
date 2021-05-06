package com.jsg.springemailtest.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailSender {

    private static final class SmtpProperties {
        static final String HOST = "mail.smtp.host";
        static final String PORT = "mail.smtp.port";
        static final String TLS_ENABLE = "mail.smtp.starttls.enable";
        static final String TLS_REQUIRED = "mail.smtp.starttls.required";
        static final String USE_AUTH = "mail.smtp.auth";
    }

    private static final Properties PROPERTIES = System.getProperties();
    private static String senderAddress;
    private static String accountPassword;
    private static boolean configured = false;

    public static void configure(String sender, String password) {
        senderAddress = sender;
        accountPassword = password;
        configured = true;
    }

    public static void configure(String smtpHost, int smtpPort, boolean useTls, String sender, String password) {
        PROPERTIES.put(SmtpProperties.HOST, smtpHost);
        PROPERTIES.put(SmtpProperties.PORT, smtpPort);
        PROPERTIES.put(SmtpProperties.TLS_ENABLE, useTls);
        PROPERTIES.put(SmtpProperties.TLS_REQUIRED, useTls);
        PROPERTIES.put(SmtpProperties.USE_AUTH, true);
        senderAddress = sender;
        accountPassword = password;
        configured = true;
    }

    public static void sendHtmlMail(String subject, String htmlContent, String recipient) throws MessagingException {
        sendHtmlMail(subject, htmlContent, recipient, null);
    }

    public static void sendHtmlMail(String subject, String htmlContent, String recipient, DataSource imageDataSource) throws MessagingException {
        if (!configured) throw new IllegalStateException("MailSender has not been configured. Please call the `configure` method before calling any other methods.");
        Message message = initializeMessage(subject, recipient);
        Multipart multipart = new MimeMultipart();
        BodyPart messagePart = createMessagePart(htmlContent);
        multipart.addBodyPart(messagePart);
        if (imageDataSource != null) {
            BodyPart imagePart = createImagePart(imageDataSource);
            multipart.addBodyPart(imagePart);
        }
        message.setContent(multipart);
        Transport.send(message);
    }

    private static Message initializeMessage(String subject, String recipient) throws MessagingException {
        Session session = getAuthenticatedSession();
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        message.setFrom(new InternetAddress(MailSender.senderAddress));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        return message;
    }

    private static BodyPart createImagePart(DataSource imageDataSource) throws MessagingException {
        BodyPart imagePart = new MimeBodyPart();
        imagePart.setDataHandler(new DataHandler(imageDataSource));
        imagePart.setHeader("Content-ID", "<image>");
        return imagePart;
    }

    private static BodyPart createMessagePart(String htmlContent) throws MessagingException {
        BodyPart messagePart = new MimeBodyPart();
        messagePart.setContent(htmlContent, "text/html");
        return messagePart;
    }

    private static Session getAuthenticatedSession() {
        return Session.getInstance(PROPERTIES, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderAddress, accountPassword);
            }
        });
    }

}
