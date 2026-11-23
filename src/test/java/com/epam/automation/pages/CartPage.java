package com.epam.automation.pages;

import com.epam.automation.components.CartRow;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.*;

public final class CartPage extends AbstractPage {
    private final By cartTable = By.id("cart_info");
    private final By emptyCartMessage = By.id("empty_cart");
    private final By rows = By.cssSelector("#cart_info_table tr[id^='product-']");
    private final By rowNames = By.cssSelector("#cart_info_table tr[id^='product-'] .cart_description h4 a");
    public CartPage(WebDriver driver) { super(driver); }
    public CartPage openCartForPrecondition() { navigateTo("https://www.automationexercise.com/view_cart"); return this; }
    public boolean isDisplayed() { return displayed(cartTable); }
    public boolean isEmpty() { return getRowCount() == 0; }
    public int getRowCount() { wait.until(d -> !d.findElements(cartTable).isEmpty() || !d.findElements(emptyCartMessage).isEmpty()); return driver.findElements(rows).size(); }
    public boolean hasProduct(String name) { wait.until(ExpectedConditions.presenceOfElementLocated(cartTable)); return driver.findElements(rowNames).stream().anyMatch(e -> e.getText().trim().equals(name)); }
    public CartRow findRow(String name) { return new CartRow(driver, name); }
    public void removeProduct(String name) { findRow(name).remove(); }
    public List<CartRow> getProductRows() {
        wait.until(ExpectedConditions.presenceOfElementLocated(cartTable));
        return driver.findElements(rowNames).stream().map(e -> new CartRow(driver, e.getText().trim())).toList();
    }
}
