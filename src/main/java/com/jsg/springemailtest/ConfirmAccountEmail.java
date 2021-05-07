package com.jsg.springemailtest;

import com.jsg.springemailtest.mail.TemplateEmail;
import org.thymeleaf.context.Context;

import javax.activation.DataSource;

public class ConfirmAccountEmail extends TemplateEmail {

    private static final String SUBJECT = "Confirm your Email";
    private static final String TEMPLATE_PATH = "/templates/email.html";
    private static final String IMAGE_PATH = "/templates/logo.png";
    private static final String IMAGE_EXTENSION = "png";
    private static final String IMAGE_MIME_TYPE = "image/png";
    private static final String TEMPLATE = loadHtmlTemplate(TEMPLATE_PATH);
    private static final DataSource IMAGE_DATA_SOURCE = loadImageDataSource(IMAGE_PATH, IMAGE_EXTENSION, IMAGE_MIME_TYPE);

    private final String confirmAccountUrl;
    private final String cancelAccountUrl;

    public ConfirmAccountEmail(String confirmAccountUrl, String cancelAccountUrl) {
        this.confirmAccountUrl = confirmAccountUrl;
        this.cancelAccountUrl = cancelAccountUrl;
    }

    @Override
    public String getSubject() {
        return SUBJECT;
    }

    @Override
    public String getHtmlContent() {
        Context context = new Context();
        context.setVariable("url", confirmAccountUrl);
        context.setVariable("cancelUrl", cancelAccountUrl);
        return TemplateEmail.populateHtmlTemplate(TEMPLATE, context);
    }

    @Override
    public DataSource[] getImages() {
        return new DataSource[]{ IMAGE_DATA_SOURCE };
    }

}
