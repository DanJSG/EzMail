package com.jsg.springemailtest.mail;

import javax.activation.DataSource;

public interface Email {

    String getSubject();
    String getHtmlContent();
    DataSource[] getImages();

}
