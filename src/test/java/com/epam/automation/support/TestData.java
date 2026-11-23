package com.epam.automation.support;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class TestData {
    private TestData() {
    }

    public static final String BLUE_TOP = "Blue Top";
    public static final String MEN_TSHIRT = "Men Tshirt";
    public static final String CONTACT_NAME = "EPAM QA Tester";
    public static final String CONTACT_EMAIL = "epam.qa.tester@example.com";
    public static final String CONTACT_SUBJECT = "EPAM Selenium assignment";
    public static final String CONTACT_MESSAGE = "Automated contact form submission with a text attachment.";
    public static final String CONFIRM_TEXT = "Press OK to proceed!";
    public static final String SUCCESS_TEXT = "Success! Your details have been submitted successfully.";

    public static Path attachment() {
        String resource = "attachments/epam-test-attachment.txt";
        URL url = TestData.class.getClassLoader().getResource(resource);
        if (url == null) {
            throw new IllegalStateException("Missing test attachment resource: " + resource);
        }
        try {
            return Paths.get(url.toURI()).toAbsolutePath();
        } catch (URISyntaxException exception) {
            throw new IllegalStateException("Cannot resolve test attachment: " + resource, exception);
        }
    }
}
