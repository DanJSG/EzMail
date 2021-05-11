package org.ezlibs.ezmail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TemplateEmailTest {

    @Test
    void loadHtmlTemplate_successfullyLoadsExistingHtmlTemplate() {
        String expectedValue =
                "<!DOCTYPE html>" + System.lineSeparator() +
                        "<html xmlns:th=\"http://www.thymeleaf.org\" lang=\"en\">" + System.lineSeparator() +
                        "<head>" + System.lineSeparator() +
                        "    <meta charset=\"UTF-8\">" + System.lineSeparator() +
                        "    <title></title>" + System.lineSeparator() +
                        "</head>" + System.lineSeparator() +
                        "<body>" + System.lineSeparator() +
                        "<!--/*@thymesVar id=\"testVal\" type=\"java.lang.String\"*/-->" + System.lineSeparator() +
                        "    <p th:text=\"${testVal}\"></p>" + System.lineSeparator() +
                        "</body>" + System.lineSeparator() +
                        "</html>";
        String path = "/test.html";
        try {
            String template = TemplateEmail.loadHtmlTemplate(path);
            Assertions.assertEquals(expectedValue, template);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail("Exception thrown; test failed.");
        }
    }

    @Test
    void loadHtmlTemplate_failsToLoadMissingHtmlTemplate() {
        String path = "/doesnotexist/test.html";
        String expectedException = "File not found.";
        try {
            String template = TemplateEmail.loadHtmlTemplate(path);
            Assertions.fail();
        } catch (IOException e) {
            Assertions.assertEquals(FileNotFoundException.class, e.getClass());
            Assertions.assertEquals(expectedException, e.getMessage());
        }
    }

    @Test
    void loadHtmlTemplate_failsToLoadWrongFileType() {
        String path = "/test.png";
        String expectedException = "Invalid file type for HTML template. Only files ending in '.html' are supported.";
        try {
            TemplateEmail.loadHtmlTemplate(path);
            Assertions.fail();
        } catch (IOException e) {
            Assertions.assertEquals(IOException.class, e.getClass());
            Assertions.assertEquals(expectedException, e.getMessage());
        }
    }

    @Test
    void loadHtmlTemplate_failsToLoadExistingTemplateWithNoExtension() {
        String path = "/test";
        String expectedException = "Invalid file type for HTML template. Only files ending in '.html' are supported.";
        try {
            TemplateEmail.loadHtmlTemplate(path);
        } catch (IOException e) {
            Assertions.assertEquals(IOException.class, e.getClass());
            Assertions.assertEquals(expectedException, e.getMessage());
        }
    }

    @Test
    void loadHtmlTemplate_failsToLoadExistingTemplateWithNothingBeforeWrongFileExtension() {
        String path = "/.test";
        String expectedException = "Invalid file type for HTML template. Only files ending in '.html' are supported.";
        try {
            TemplateEmail.loadHtmlTemplate(path);
        } catch (IOException e) {
            Assertions.assertEquals(IOException.class, e.getClass());
            Assertions.assertEquals(expectedException, e.getMessage());
        }
    }

    @Test
    void loadHtmlTemplate_failsToLoadsExistingTemplateWithNothingBeforeCorrectFileExtension() {
        String expectedValue =
                "<!DOCTYPE html>" + System.lineSeparator() +
                        "<html xmlns:th=\"http://www.thymeleaf.org\" lang=\"en\">" + System.lineSeparator() +
                        "<head>" + System.lineSeparator() +
                        "    <meta charset=\"UTF-8\">" + System.lineSeparator() +
                        "    <title></title>" + System.lineSeparator() +
                        "</head>" + System.lineSeparator() +
                        "<body>" + System.lineSeparator() +
                        "<!--/*@thymesVar id=\"testVal\" type=\"java.lang.String\"*/-->" + System.lineSeparator() +
                        "    <p th:text=\"${testVal}\"></p>" + System.lineSeparator() +
                        "</body>" + System.lineSeparator() +
                        "</html>";
        String path = "/.html";
        try {
            String template = TemplateEmail.loadHtmlTemplate(path);
            Assertions.assertEquals(expectedValue, template);
        } catch (IOException e) {
            Assertions.fail("Exception thrown; test failed.");
        }
    }

    @Test
    void populateHtmlTemplate_successfullyPopulatesTemplateVariables() {
        String expectedValue =
                "<!DOCTYPE html>" + System.lineSeparator() +
                        "<html lang=\"en\">" + System.lineSeparator() +
                        "<head>" + System.lineSeparator() +
                        "    <meta charset=\"UTF-8\">" + System.lineSeparator() +
                        "    <title></title>" + System.lineSeparator() +
                        "</head>" + System.lineSeparator() +
                        "<body>" + System.lineSeparator() + System.lineSeparator() +
                        "    <p>test</p>" + System.lineSeparator() +
                        "</body>" + System.lineSeparator() +
                        "</html>";
        try {
            String template = TemplateEmail.loadHtmlTemplate("/test.html");
            Map<String, Object> map = new HashMap<>();
            map.put("testVal", "test");
            String htmlContent = TemplateEmail.populateHtmlTemplate(template, map);
            Assertions.assertEquals(expectedValue, htmlContent);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    void populateHtmlTemplate_successfullyPopulateTemplateWithEmptyValues() {
        String expectedValue =
                "<!DOCTYPE html>" + System.lineSeparator() +
                        "<html lang=\"en\">" + System.lineSeparator() +
                        "<head>" + System.lineSeparator() +
                        "    <meta charset=\"UTF-8\">" + System.lineSeparator() +
                        "    <title></title>" + System.lineSeparator() +
                        "</head>" + System.lineSeparator() +
                        "<body>" + System.lineSeparator() + System.lineSeparator() +
                        "    <p></p>" + System.lineSeparator() +
                        "</body>" + System.lineSeparator() +
                        "</html>";
        try {
            String template = TemplateEmail.loadHtmlTemplate("/test.html");
            Map<String, Object> map = new HashMap<>();
            String htmlContent = TemplateEmail.populateHtmlTemplate(template, map);
            Assertions.assertEquals(expectedValue, htmlContent);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    void populateHtmlTemplate_successfullyPopulateInvalidTemplate() {
        String expectedValue =
                "<!DOCTYPE html>" + System.lineSeparator() +
                        "<html lang=\"en\">" + System.lineSeparator() +
                        "<head>" + System.lineSeparator() +
                        "    <meta charset=\"UTF-8\">" + System.lineSeparator() +
                        "    <title></title>" + System.lineSeparator() +
                        "</head>" + System.lineSeparator() +
                        "<body>" + System.lineSeparator() +
                        "    <p></p>" + System.lineSeparator() +
                        "</body>" + System.lineSeparator() +
                        "</html>";
        try {
            String template = TemplateEmail.loadHtmlTemplate("/invalidtest.html");
            Map<String, Object> map = new HashMap<>();
            map.put("testVal", "test");
            String htmlContent = TemplateEmail.populateHtmlTemplate(template, map);
            Assertions.assertEquals(expectedValue, htmlContent);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

}
