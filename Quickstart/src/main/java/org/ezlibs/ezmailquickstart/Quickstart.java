package org.ezlibs.ezmailquickstart;


import com.jsg.ezmail.EmailSender;

import javax.mail.MessagingException;

public class Quickstart {

    public static void main(String[] args) {

        // The host of your SMTP server. For example, if you were using Gmail, this would be "smtp.gmail.com".
        // Change this to your host
        String host = "your.host.com";

        // The SMTP server port - this information will be provided by your SMTP server host
        // Change this to your host's port (although 587 is the usual port)
        int port = 587;

        // Whether or not you should use TLS
        boolean useTls = true;

        // The email address and password to use when authenticating with the SMTP server
        // Change these to your credentials
        String user = "youremail@domain.com";
        String password = "CHANGE ME";

        // The the email will show as being sent from
        String senderName = "EzMail Quickstart";

        // Configure the EmailSender. This only needs to be called once globally
        EmailSender.configure(host, port, useTls, user, password, senderName);

        // Change this to your name
        String name = "CHANGE ME";

        // Example text which will be shown in the email - try changing it to something else
        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

        // The path to an image which will be shown in the email
        String imagePath = "/templates/quickstart.jpg";

        // Create the email
        ExampleEmail email = new ExampleEmail(name, text, imagePath);

        // The address to send the email to. We recommend making this your own email address whilst testing
        String to = "youremail@domain.com";

        try {
            System.out.println("Sending email...");
            EmailSender.send(email, to);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        System.out.println("Email successfully sent!");

    }

}
