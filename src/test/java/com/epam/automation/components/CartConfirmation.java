package com.epam.automation.components;

import com.epam.automation.pages.CartPage;
import com.epam.automation.pages.ProductsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class CartConfirmation {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By modal = By.id("cartModal");
    private final By continueButton = By.cssSelector("#cartModal button.close-modal");
    private final By viewCart = By.cssSelector("#cartModal a[href='/view_cart']");

    public CartConfirmation(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public boolean isVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(modal)).isDisplayed();
        } catch (TimeoutException exception) {
            return false;
        }
    }

    public ProductsPage continueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modal));
        return new ProductsPage(driver);
    }

    public CartPage openViewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCart)).click();
        return new CartPage(driver);
    }
}
