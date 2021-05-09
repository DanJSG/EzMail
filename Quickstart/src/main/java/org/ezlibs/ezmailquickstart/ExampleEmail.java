package org.ezlibs.ezmailquickstart;

import com.jsg.ezmail.TemplateEmail;

import javax.activation.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExampleEmail extends TemplateEmail {

    private final String name;
    private final String exampleText;
    private final DataSource image;

    private static final String SUBJECT = "EzMail Quickstart";
    private static final String TEMPLATE = loadTemplate();

    public ExampleEmail(String name, String exampleText, String imagePath) {
        this.name = name;
        this.exampleText = exampleText;
        this.image = TemplateEmail.loadImageDataSource(imagePath, "jpg", "image/jpeg");
    }

    private static String loadTemplate() {
        try {
            return TemplateEmail.loadHtmlTemplate("/templates/quickstart.html");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getSubject() {
        return SUBJECT;
    }

    @Override
    public String getHtmlContent() {
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("name", name);
        contentMap.put("exampleText", exampleText);
        return TemplateEmail.populateHtmlTemplate(TEMPLATE, contentMap);
    }

    @Override
    public DataSource[] getImages() {
        return new DataSource[] { image };
    }
}
