package com.jsg.springemailtest.mail.utils;

import com.jsg.springemailtest.mail.MailSender;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ImageResourceReader {

    public static byte[] getAsBytes(String resourcePath, String extension) throws IOException {
        InputStream imageStream = MailSender.class.getResourceAsStream(resourcePath);
        BufferedImage image = ImageIO.read(imageStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, extension, outputStream);
        outputStream.flush();
        byte[] imageBytes = outputStream.toByteArray();
        outputStream.close();
        return imageBytes;
    }

}
