package com.jsg.springemailtest.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Sends HTML emails using SMTP. The {@code configure} method must have been called at least once globally before email
 * can be sent.
 */
public final class EmailSender {

    /**
     * SMTP System property name constants.
     */
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

    /**
     * Configure the global SMTP properties for sending mail.
     *
     * @param smtpHost the host of the SMTP server
     * @param smtpPort the port of the SMTP server
     * @param useTls should TLS be used when connecting to the SMTP server?
     * @param account the authorization email address
     * @param password the password to use during authorization
     * @param name the name the email will be sent with
     */
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

    /**
     * Send a HTML email using SMTP to a specified email address.
     * @param email the email
     * @param to the address to send the email to
     * @throws IllegalStateException if the EmailSender has not be configured globally
     * @throws MessagingException if something goes wrong when connecting to the SMTP server or sending the mail
     */
    public static void send(Email email, String to) throws MessagingException, IllegalStateException {
        if (!configured) throw new IllegalStateException("EmailSender has not been configured. Please call the `configure` method before calling any other methods.");
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

    /**
     * Authenticate with the SMTP server and create a MIME message with a provided subject and recipient.
     *
     * @param subject the subject line of the email
     * @param recipient the recipient's email address
     * @return the email {@code Message} object
     * @throws MessagingException if something goes wrong when authenticating or building the message
     */
    private static Message initializeMessage(String subject, String recipient) throws MessagingException {
        Session session = getAuthenticatedSession();
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        message.setFrom(new InternetAddress(EmailSender.sender));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        return message;
    }

    /**
     * Create an image attachment part with a specific Content-ID (CID) value. The CID value given to the image
     * will be &lt;image-{index}&gt;
     *
     * @param imageDataSource the image data source
     * @param index the index of the image in the email
     * @return the image email body part
     * @throws MessagingException if something goes wrong when adding the image
     */
    private static BodyPart createImagePart(DataSource imageDataSource, int index) throws MessagingException {
        BodyPart imagePart = new MimeBodyPart();
        imagePart.setDataHandler(new DataHandler(imageDataSource));
        imagePart.setHeader("Content-ID", "<image-" + index + ">");
        return imagePart;
    }

    /**
     * Create the main HTML body content of the email.
     *
     * @param htmlContent the HTML content to add to the body of the email
     * @return the HTML email body part
     * @throws MessagingException if something goes wrong whilst adding the HTML
     */
    private static BodyPart createMessagePart(String htmlContent) throws MessagingException {
        BodyPart messagePart = new MimeBodyPart();
        messagePart.setContent(htmlContent, "text/html");
        return messagePart;
    }

    /**
     * Authenticate the SMTP session using a username and password.
     *
     * @return an authenticated SMTP session
     */
    private static Session getAuthenticatedSession() {
        return Session.getInstance(PROPERTIES, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderAccount, accountPassword);
            }
        });
    }

}
