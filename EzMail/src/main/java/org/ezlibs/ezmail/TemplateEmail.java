package org.ezlibs.ezmail;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.mail.util.ByteArrayDataSource;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A HTML email using a Thymeleaf template from the resources folder. Must be extended to instantiate.
 * Provides helper methods for loading a template, loading images for use in the email, and populating the HTML template.
 */
public abstract class TemplateEmail implements Email {

    private static final TemplateEngine ENGINE = new TemplateEngine();

    /**
     * Populate a Thymeleaf HTML template with the values provided in the context.
     *
     * @param template the HTML template to populate with values
     * @param varMap a map of template variable names and their corresponding values
     * @return the populate HTML content as a {@code String}
     */
    protected static String populateHtmlTemplate(String template, Map<String, Object> varMap) {
        Context context = new Context();
        context.setVariables(varMap);
        return TemplateEmail.ENGINE.process(template, context);
    }

    /**
     * Load an image from the application's resources folder and open it as a data source.
     *
     * @param resourcePath the relative path to the image resource
     * @param extension    the file extension
     * @param mimeType     the MIME type of the file
     * @return the image as a {@code DataSource} object, or {@code null} if loading the image failed
     */
    protected static DataSource loadImageDataSource(String resourcePath, String extension, String mimeType) {
        try {
            byte[] imageBytes = getImageResourceAsBytes(resourcePath, extension);
            return new ByteArrayDataSource(imageBytes, mimeType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Load a Thymeleaf HTML template from the application's resource folder and return it's content as a string.
     *
     * @param templatePath the relative path to the HTML template
     * @return the HTML template's content as a {@code String}
     * @throws IOException if the file has an extension other than '.html'
     * @throws FileNotFoundException if the file does not exist
     */
    protected static String loadHtmlTemplate(String templatePath) throws IOException {
        if (!checkFileExtension(templatePath))
            throw new IOException("Invalid file type for HTML template. Only files ending in '.html' are supported.");
        InputStream templateStream = TemplateEmail.class.getResourceAsStream(templatePath);
        if (templateStream == null) throw new FileNotFoundException("File not found.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(templateStream));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Load an image from the application's resources folder, and convert it to a byte array without writing to disk.
     *
     * @param resourcePath the path to the image
     * @param extension    the image's file extension
     * @return a byte array representing the image
     * @throws IOException if their is an error whilst reading the image or writing it to the output stream
     */
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

    /**
     * Checks that the file path has a .html file extension. Returns {@code true} if it does, {@code false} otherwise.
     * @param path the path of the file to check
     * @return {@code true} if it is a .html file, {@code false} if it is not
     */
    private static boolean checkFileExtension(String path) {
        int index = path.lastIndexOf(".");
        if (index == -1) return false;
        return (index > 0 ? path.substring(index + 1) : path.substring(index)).equals("html");
    }

}