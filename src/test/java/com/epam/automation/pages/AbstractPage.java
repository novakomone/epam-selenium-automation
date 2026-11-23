package com.epam.automation.pages;

import org.openqa.selenium.*;
import com.epam.automation.components.ConsentDialog;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public abstract class AbstractPage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected AbstractPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }
    protected WebElement visible(By locator) { return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); }
    protected WebElement clickable(By locator) { return wait.until(ExpectedConditions.elementToBeClickable(locator)); }
    protected void click(By locator) {
        wait.until(d -> {
            WebElement element = d.findElement(locator);
            if (!element.isDisplayed() || !element.isEnabled()) return false;
            ((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
            try {
                element.click();
                return true;
            } catch (ElementClickInterceptedException | StaleElementReferenceException intercepted) {
                return false;
            }
        });
    }
    protected String currentUrl() { return driver.getCurrentUrl(); }
    protected void type(By locator, String value) { WebElement element = visible(locator); element.clear(); element.sendKeys(value); }
    protected String value(By locator) { return visible(locator).getAttribute("value"); }
    protected void navigateTo(String url) { driver.navigate().to(url); new ConsentDialog(driver).acceptIfPresent(); }
    protected boolean displayed(By locator) {
        try { return visible(locator).isDisplayed(); } catch (TimeoutException exception) { return false; }
    }
}
