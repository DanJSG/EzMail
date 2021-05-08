package com.jsg.springemailtest;

import com.jsg.ezmail.TemplateEmail;

import javax.activation.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfirmAccountEmail extends TemplateEmail {

    private static final String SUBJECT = "Confirm your Email";
    private static final String TEMPLATE_PATH = "/templates/email.html";
    private static final String IMAGE_PATH = "/templates/logo.png";
    private static final String IMAGE_EXTENSION = "png";
    private static final String IMAGE_MIME_TYPE = "image/png";
    private static final String TEMPLATE = loadTemplate();

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
        Map<String, Object> varMap = new HashMap<>();
        varMap.put("url", confirmAccountUrl);
        varMap.put("cancelUrl", cancelAccountUrl);
        return TemplateEmail.populateHtmlTemplate(TEMPLATE, varMap);
    }

    @Override
    public DataSource[] getImages() {
        return new DataSource[]{ IMAGE_DATA_SOURCE };
    }

    private static String loadTemplate() {
        String template = null;
        try {
            template = loadHtmlTemplate(TEMPLATE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }

}
