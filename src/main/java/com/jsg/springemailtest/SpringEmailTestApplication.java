package com.jsg.springemailtest;

import com.jsg.springemailtest.mail.Email;
import com.jsg.springemailtest.mail.EmailSender;
import com.jsg.springemailtest.providers.MailPropertyProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.MessagingException;

@SpringBootApplication
public class SpringEmailTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEmailTestApplication.class, args);
        int port = MailPropertyProvider.getPort();
        String host = MailPropertyProvider.getHost();
        boolean useTls = MailPropertyProvider.getUseTls();
        String account = MailPropertyProvider.getAccount();
        String password = MailPropertyProvider.getPassword();
        String name = MailPropertyProvider.getName();
        EmailSender.configure(host, port, useTls, account, password, name);
        Email email = new ConfirmAccountEmail("http://localhost:3010/auth/confirm?code=24985h98ye7bvn9p85e", "http://localhost:3010/auth/cancel?code=24985h98ye7bvn9p85e");
        try {
            EmailSender.send(email, "dtj503@york.ac.uk");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
