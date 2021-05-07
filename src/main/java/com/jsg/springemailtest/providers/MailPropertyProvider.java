package com.jsg.springemailtest.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MailPropertyProvider {

    private static final class MailProperties {
        private static final String HOST = "host";
        private static final String PORT = "port";
        private static final String TLS = "tls";
        private static final String ACCOUNT = "account";
        private static final String PASSWORD = "password";
        private static final String NAME = "name";
    }

    private static final Map<String, Object> MAIL_PROPERTIES = new HashMap<>();

    @Autowired
    private MailPropertyProvider(@Value("${MAIL_HOST}") String host,
                                 @Value("${MAIL_PORT}") int port,
                                 @Value("${MAIL_USE_TLS}") boolean useTls,
                                 @Value("${MAIL_ACCOUNT}") String emailAccount,
                                 @Value("${MAIL_PASSWORD}") String password,
                                 @Value("${MAIL_SENDER_NAME}") String name) {
        MAIL_PROPERTIES.put(MailProperties.HOST, host);
        MAIL_PROPERTIES.put(MailProperties.PORT, port);
        MAIL_PROPERTIES.put(MailProperties.TLS, useTls);
        MAIL_PROPERTIES.put(MailProperties.ACCOUNT, emailAccount);
        MAIL_PROPERTIES.put(MailProperties.PASSWORD, password);
        MAIL_PROPERTIES.put(MailProperties.NAME, name);
    }

    public static String getHost() {
        return (String) MAIL_PROPERTIES.get(MailProperties.HOST);
    }

    public static int getPort() {
        return (int) MAIL_PROPERTIES.get(MailProperties.PORT);
    }

    public static boolean getUseTls() {
        return (boolean) MAIL_PROPERTIES.get(MailProperties.TLS);
    }

    public static String getAccount() {
        return (String) MAIL_PROPERTIES.get(MailProperties.ACCOUNT);
    }

    public static String getPassword() {
        return (String) MAIL_PROPERTIES.get(MailProperties.PASSWORD);
    }

    public static String getName() {
        return (String) MAIL_PROPERTIES.get(MailProperties.NAME);
    }

}
