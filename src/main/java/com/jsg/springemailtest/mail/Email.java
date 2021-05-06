package com.jsg.springemailtest.mail;

public interface Email {

    boolean send(String to);

    String getHtmlContent();

}
