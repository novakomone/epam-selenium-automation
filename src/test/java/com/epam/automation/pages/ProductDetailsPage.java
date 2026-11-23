package com.epam.automation.pages;

import com.epam.automation.components.CartConfirmation;
import org.openqa.selenium.*;

public final class ProductDetailsPage extends AbstractPage {
    private final By name = By.cssSelector(".product-information h2");
    private final By quantity = By.id("quantity");
    private final By add = By.cssSelector("button.cart");
    public ProductDetailsPage(WebDriver driver) { super(driver); }
    public String getProductName() { return visible(name).getText().trim(); }
    public void setQuantity(int count) { if (count < 1) throw new IllegalArgumentException("Quantity must be positive"); type(quantity, String.valueOf(count)); }
    public int getQuantity() { return Integer.parseInt(value(quantity)); }
    public CartConfirmation addToCart() { click(add); return new CartConfirmation(driver); }
}
