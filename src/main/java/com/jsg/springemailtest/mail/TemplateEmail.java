package com.jsg.springemailtest.mail;

import com.jsg.springemailtest.ConfirmAccountEmail;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.mail.util.ByteArrayDataSource;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.stream.Collectors;

public abstract class TemplateEmail implements Email {

    protected static final TemplateEngine ENGINE = new TemplateEngine();

    protected static String populateHtmlTemplate(String template, Context context) {
        return TemplateEmail.ENGINE.process(template, context);
    }

    protected static DataSource loadImageDataSource(String resourcePath, String extension, String mimeType) {
        try {
            byte[] imageBytes = getImageResourceAsBytes(resourcePath, extension);
            return new ByteArrayDataSource(imageBytes, mimeType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static String loadHtmlTemplate(String templatePath) {
        InputStream templateStream = ConfirmAccountEmail.class.getResourceAsStream(templatePath);
        if (templateStream == null) return null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(templateStream));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private static byte[] getImageResourceAsBytes(String resourcePath, String extension) throws IOException {
        InputStream imageStream = EmailSender.class.getResourceAsStream(resourcePath);
        BufferedImage image = ImageIO.read(imageStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, extension, outputStream);
        outputStream.flush();
        byte[] imageBytes = outputStream.toByteArray();
        outputStream.close();
        return imageBytes;
    }

}
