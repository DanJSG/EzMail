package com.jsg.springemailtest;

import com.jsg.springemailtest.mail.Email;
import com.jsg.springemailtest.mail.MailSender;
import com.jsg.springemailtest.providers.MailPropertyProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringEmailTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEmailTestApplication.class, args);
        int port = MailPropertyProvider.getPort();
        String host = MailPropertyProvider.getHost();
        boolean useTls = MailPropertyProvider.getUseTls();
        String account = MailPropertyProvider.getAccount();
        String password = MailPropertyProvider.getPassword();
        MailSender.configure(host, port, useTls, account, password);
        Email email = new ConfirmAccountEmail("http://localhost:3010/auth/confirm?code=24985h98ye7bvn9p85e", "http://localhost:3010/auth/cancel?code=24985h98ye7bvn9p85e");
        email.send("dtj503@york.ac.uk");
    }

}
