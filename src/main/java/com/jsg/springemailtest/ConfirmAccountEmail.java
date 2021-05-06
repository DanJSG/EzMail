package com.jsg.springemailtest;

import com.jsg.springemailtest.mail.TemplateEmail;
import com.jsg.springemailtest.mail.MailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.DataSource;
import javax.mail.MessagingException;

public class ConfirmAccountEmail extends TemplateEmail {

    private static final String SUBJECT = "Confirm your Email";
    private static final String TEMPLATE_PATH = "/templates/email.html";
    private static final String IMAGE_PATH = "/templates/logo.png";
    private static final String IMAGE_EXTENSION = "png";
    private static final String IMAGE_MIME_TYPE = "image/png";
    private static final TemplateEngine ENGINE = new TemplateEngine();
    private static final String TEMPLATE;
    private static final DataSource IMAGE_DATA_SOURCE;

    private final String confirmAccountUrl;
    private final String cancelAccountUrl;

    public ConfirmAccountEmail(String confirmAccountUrl, String cancelAccountUrl) {
        this.confirmAccountUrl = confirmAccountUrl;
        this.cancelAccountUrl = cancelAccountUrl;
    }

    static {
        IMAGE_DATA_SOURCE = loadImageDataSource(IMAGE_PATH, IMAGE_EXTENSION, IMAGE_MIME_TYPE);
        TEMPLATE = loadHtmlTemplate(TEMPLATE_PATH);
    }

    @Override
    public boolean send(String to) {
        try {
            String htmlContent = generateEmail(confirmAccountUrl, cancelAccountUrl);
            MailSender.sendHtmlMail(SUBJECT, htmlContent, to, IMAGE_DATA_SOURCE);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getHtmlContent() {
        return generateEmail(confirmAccountUrl, cancelAccountUrl);
    }

    private static String generateEmail(String confirmAccountUrl, String cancelAccountUrl) {
        Context context = new Context();
        context.setVariable("url", confirmAccountUrl);
        context.setVariable("cancelUrl", cancelAccountUrl);
        return ConfirmAccountEmail.ENGINE.process(TEMPLATE, context);
    }

}
