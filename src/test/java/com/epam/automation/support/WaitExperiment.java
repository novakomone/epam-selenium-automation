package com.epam.automation.support;

import com.epam.automation.driver.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

/** Standalone diagnostic; never included in testng.xml. */
public final class WaitExperiment {
    private WaitExperiment() { }
    public static void main(String[] args) {
        compare("implicit", true);
        compare("explicit", false);
    }
    private static void compare(String label, boolean implicit) {
        WebDriver driver = DriverFactory.createDriver(System.getProperty("browser", "chrome"));
        try {
            driver.get("https://www.automationexercise.com/");
            driver.manage().timeouts().implicitlyWait(implicit ? Duration.ofSeconds(2) : Duration.ZERO);
            long start = System.nanoTime();
            try {
                By missing = By.id("epam-wait-diagnostic-missing-element");
                if (implicit) driver.findElement(missing);
                else new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.presenceOfElementLocated(missing));
            } catch (NoSuchElementException | TimeoutException expected) {
                System.out.printf("%s-only: %.3f seconds (expected missing element)%n", label, (System.nanoTime() - start) / 1_000_000_000.0);
            }
        } finally { driver.quit(); }
    }
}
