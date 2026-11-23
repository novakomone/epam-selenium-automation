package com.epam.automation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.nio.file.Path;
import java.time.Duration;

public final class ContactUsPage extends AbstractPage {
    private final By form = By.cssSelector(".contact-form form");
    private final By name = By.name("name");
    private final By email = By.name("email");
    private final By subject = By.name("subject");
    private final By message = By.id("message");
    private final By attachment = By.name("upload_file");
    private final By submit = By.name("submit");
    private final By success = By.cssSelector(".status.alert-success");
    public ContactUsPage(WebDriver driver) { super(driver); }
    public boolean isFormDisplayed() { return displayed(form); }
    public void enterName(String text) { type(name, text); }
    public String getName() { return value(name); }
    public void enterEmail(String text) { type(email, text); }
    public String getEmail() { return value(email); }
    public void enterSubject(String text) { type(subject, text); }
    public String getSubject() { return value(subject); }
    public void enterMessage(String text) { type(message, text); }
    public String getMessage() { return value(message); }
    public void attachFile(Path path) { visible(attachment).sendKeys(path.toAbsolutePath().toString()); }
    public String getSelectedFileName() {
        String value = value(attachment).replace('\\', '/');
        return value.substring(value.lastIndexOf('/') + 1);
    }
    public void submitForm() { click(submit); }
    public boolean isConfirmationAlertPresent() {
        try { new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.alertIsPresent()); return true; }
        catch (TimeoutException exception) { return false; }
    }
    public String getConfirmationText() { return wait.until(ExpectedConditions.alertIsPresent()).getText(); }
    public void acceptConfirmation() { wait.until(ExpectedConditions.alertIsPresent()).accept(); }
    public boolean isSuccessMessageDisplayed() { return displayed(success); }
    public String getSuccessMessage() { return visible(success).getText().trim(); }
}
