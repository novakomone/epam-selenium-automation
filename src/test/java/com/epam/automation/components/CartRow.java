package com.epam.automation.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class CartRow {
    private final WebDriverWait wait;
    private final By row;
    private final By name = By.cssSelector(".cart_description h4 a");
    private final By quantity = By.cssSelector(".cart_quantity button");
    private final By remove = By.cssSelector(".cart_quantity_delete");

    public CartRow(WebDriver driver, String productName) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        row = By.xpath("//tr[starts-with(@id,'product-')]" +
                "[.//td[contains(@class,'cart_description')]//h4/a[normalize-space()=" +
                xpathLiteral(productName) + "]]");
    }

    private static String xpathLiteral(String text) {
        if (!text.contains("'")) {
            return "'" + text + "'";
        }
        if (!text.contains("\"")) {
            return "\"" + text + "\"";
        }
        String[] parts = text.split("'", -1);
        StringBuilder expression = new StringBuilder("concat(");
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                expression.append(", \"'\", ");
            }
            expression.append("'").append(parts[i]).append("'");
        }
        return expression.append(")").toString();
    }

    private WebElement root() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(row));
    }

    public String getName() {
        return root().findElement(name).getText().trim();
    }

    public int getQuantity() {
        return Integer.parseInt(root().findElement(quantity).getText().trim());
    }

    public void remove() {
        root().findElement(remove).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(row));
    }
}
