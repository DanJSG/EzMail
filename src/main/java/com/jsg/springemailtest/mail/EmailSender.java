package com.jsg.springemailtest.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailSender {

    private static final class SmtpProperties {
        static final String HOST = "mail.smtp.host";
        static final String PORT = "mail.smtp.port";
        static final String TLS_ENABLE = "mail.smtp.starttls.enable";
        static final String TLS_REQUIRED = "mail.smtp.starttls.required";
        static final String USE_AUTH = "mail.smtp.auth";
    }

    private static final Properties PROPERTIES = System.getProperties();
    private static String sender;
    private static String senderAccount;
    private static String accountPassword;
    private static boolean configured = false;

    public static void configure(String smtpHost, int smtpPort, boolean useTls, String account, String password, String name) {
        PROPERTIES.put(SmtpProperties.HOST, smtpHost);
        PROPERTIES.put(SmtpProperties.PORT, smtpPort);
        PROPERTIES.put(SmtpProperties.TLS_ENABLE, useTls);
        PROPERTIES.put(SmtpProperties.TLS_REQUIRED, useTls);
        PROPERTIES.put(SmtpProperties.USE_AUTH, true);
        senderAccount = account;
        accountPassword = password;
        sender = name + "<" + senderAccount + ">";
        configured = true;
    }

    public static void send(Email email, String to) throws MessagingException {
        if (!configured) throw new IllegalStateException("MailSender has not been configured. Please call the `configure` method before calling any other methods.");
        Message message = initializeMessage(email.getSubject(), to);
        Multipart multipart = new MimeMultipart();
        BodyPart messagePart = createMessagePart(email.getHtmlContent());
        multipart.addBodyPart(messagePart);
        if (email.getImages() != null) {
            for(int i = 0; i < email.getImages().length; i++) {
                BodyPart imagePart = createImagePart(email.getImages()[i], i);
                multipart.addBodyPart(imagePart);
            }
        }
        message.setContent(multipart);
        Transport.send(message);
    }

    private static Message initializeMessage(String subject, String recipient) throws MessagingException {
        Session session = getAuthenticatedSession();
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        message.setFrom(new InternetAddress(EmailSender.sender));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        return message;
    }

    private static BodyPart createImagePart(DataSource imageDataSource, int index) throws MessagingException {
        BodyPart imagePart = new MimeBodyPart();
        imagePart.setDataHandler(new DataHandler(imageDataSource));
        imagePart.setHeader("Content-ID", "<image-" + index + ">");
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
                return new PasswordAuthentication(senderAccount, accountPassword);
            }
        });
    }

}
