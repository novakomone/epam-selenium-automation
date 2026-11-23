package com.epam.automation.components;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Handles the optional Google Funding Choices consent dialog in a fresh browser profile. */
public final class ConsentDialog {
    private final WebDriver driver;
    private final By overlay = By.cssSelector(".fc-dialog-overlay");
    // Conditional fallback: confirm this selector in DevTools when the dialog appears.
    private final By consent = By.cssSelector("button.fc-cta-consent");

    public ConsentDialog(WebDriver driver) {
        this.driver = driver;
    }

    public void acceptIfPresent() {
        WebDriverWait detection = new WebDriverWait(driver, Duration.ofSeconds(4));
        try {
            detection.until(ExpectedConditions.visibilityOfElementLocated(overlay));
        } catch (TimeoutException absent) {
            return;
        }
        WebDriverWait interaction = new WebDriverWait(driver, Duration.ofSeconds(12));
        try {
            interaction.until(ExpectedConditions.elementToBeClickable(consent)).click();
            interaction.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
        } catch (TimeoutException exception) {
            throw new IllegalStateException("Consent overlay is present but could not be accepted. Verify button.fc-cta-consent in DevTools.", exception);
        }
    }
}
