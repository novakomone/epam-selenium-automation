package com.epam.automation.components;

import com.epam.automation.pages.ProductDetailsPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public final class ProductCard {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By card;
    private final By detail = By.cssSelector("a[href^='/product_details/']");
    private final By overlayAdd = By.cssSelector(".product-overlay a.add-to-cart[data-product-id]");
    public ProductCard(WebDriver driver, String productName) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        // Scoped by a product-name paragraph rather than positional product index.
        this.card = By.xpath("//div[contains(concat(' ',normalize-space(@class),' '),' product-image-wrapper ')][.//div[contains(@class,'productinfo')]//p[normalize-space()=" + xpathLiteral(productName) + "]]");
    }
    private static String xpathLiteral(String value) {
        if (!value.contains("'")) return "'" + value + "'";
        if (!value.contains("\"")) return "\"" + value + "\"";
        throw new IllegalArgumentException("Unsupported quote characters in product name");
    }
    private WebElement root() { return wait.until(ExpectedConditions.presenceOfElementLocated(card)); }
    public boolean isVisible() { return wait.until(ExpectedConditions.visibilityOfElementLocated(card)).isDisplayed(); }
    public ProductDetailsPage openDetails() {
        WebElement link = root().findElement(detail);
        String target = link.getAttribute("href");
        if (target == null || target.isBlank()) {
            throw new IllegalStateException("Product details link has no href");
        }

        new Actions(driver).moveToElement(link).perform();
        wait.until(d -> {
            try {
                root().findElement(detail).click();
                return true;
            } catch (ElementClickInterceptedException | StaleElementReferenceException exception) {
                return false;
            }
        });
        wait.until(d -> d.getCurrentUrl().startsWith(target));
        return new ProductDetailsPage(driver);
    }

    public void hover() { new Actions(driver).moveToElement(root().findElement(By.cssSelector(".productinfo"))).perform(); }
    public boolean isOverlayAddControlAvailable() {
        try { return wait.until(ExpectedConditions.elementToBeClickable(root().findElement(overlayAdd))).isDisplayed(); }
        catch (TimeoutException exception) { return false; }
    }
    public CartConfirmation addFromOverlay() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(root().findElement(overlayAdd)));
        button.click(); return new CartConfirmation(driver);
    }
    public CartConfirmation hoverAndAddToCart() { hover(); return addFromOverlay(); }
}
