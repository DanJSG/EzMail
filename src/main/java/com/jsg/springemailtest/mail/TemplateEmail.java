package com.jsg.springemailtest.mail;

import com.jsg.springemailtest.ConfirmAccountEmail;
import com.jsg.springemailtest.mail.utils.ImageResourceReader;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public abstract class TemplateEmail implements Email {

    protected static DataSource loadImageDataSource(String resourcePath, String extension, String mimeType) {
        try {
            byte[] imageBytes = ImageResourceReader.getAsBytes(resourcePath, extension);
            return new ByteArrayDataSource(imageBytes, mimeType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static String loadHtmlTemplate(String templatePath) {
        InputStream templateStream = ConfirmAccountEmail.class.getResourceAsStream("/templates/email.html");
        if (templatePath == null) return null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(templateStream));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

}
