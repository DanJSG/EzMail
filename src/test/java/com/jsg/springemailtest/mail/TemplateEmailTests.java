package com.jsg.springemailtest.mail;

import jdk.nashorn.internal.ir.CatchNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TemplateEmailTests {

    @Test
    void loadHtmlTemplate_successfullyLoadsExistingHtmlTemplate() {
        String expectedValue = "<!DOCTYPE html>\r\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\" lang=\"en\">\r\n" +
                "<head>\r\n" +
                "    <meta charset=\"UTF-8\">\r\n" +
                "    <title></title>\r\n" +
                "</head>\r\n" +
                "<body>\r\n" +
                "<!--/*@thymesVar id=\"testVal\" type=\"java.lang.String\"*/-->\r\n" +
                "    <p th:text=\"${testVal}\"></p>\r\n" +
                "</body>\r\n" +
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
            Assertions.assertEquals(e.getClass(), FileNotFoundException.class);
            Assertions.assertEquals(e.getMessage(), expectedException);
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
            Assertions.assertEquals(e.getClass(), IOException.class);
            Assertions.assertEquals(e.getMessage(), expectedException);
        }
    }

    @Test
    void loadHtmlTemplate_failsToLoadExistingTemplateWithNoExtension() {
        String path = "/test";
        String expectedException = "Invalid file type for HTML template. Only files ending in '.html' are supported.";
        try {
            TemplateEmail.loadHtmlTemplate(path);
        } catch (IOException e) {
            Assertions.assertEquals(e.getClass(), IOException.class);
            Assertions.assertEquals(e.getMessage(), expectedException);
        }
    }

    @Test
    void loadHtmlTemplate_failsToLoadExistingTemplateWithNothingBeforeWrongFileExtension() {
        String path = "/.test";
        String expectedException = "Invalid file type for HTML template. Only files ending in '.html' are supported.";
        try {
            TemplateEmail.loadHtmlTemplate(path);
        } catch (IOException e) {
            Assertions.assertEquals(e.getClass(), IOException.class);
            Assertions.assertEquals(e.getMessage(), expectedException);
        }
    }

    @Test
    void loadHtmlTemplate_failsToLoadsExistingTemplateWithNothingBeforeCorrectFileExtension() {
        String path = "/.html";
        String expectedException = "File not found.";
        try {
            TemplateEmail.loadHtmlTemplate(path);
            Assertions.fail("No exception thrown; test failed.");
        } catch (IOException e) {
            Assertions.assertEquals(e.getClass(), FileNotFoundException.class);
            Assertions.assertEquals(e.getMessage(), expectedException);
        }
    }

}
