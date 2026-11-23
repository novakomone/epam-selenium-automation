package com.epam.automation.pages;

import org.openqa.selenium.*;

public final class HomePage extends AbstractPage {
    private static final String BASE_URL = "https://www.automationexercise.com/";
    private final By homeLogo = By.cssSelector("header .logo a[href='/'] img");
    private final By productsLink = By.cssSelector("a[href='/products']");
    private final By contactLink = By.cssSelector("a[href='/contact_us']");
    public HomePage(WebDriver driver) { super(driver); }
    public HomePage openHome() { navigateTo(BASE_URL); return this; }
    public boolean isDisplayed() {
        return (currentUrl().equals(BASE_URL) || currentUrl().equals("https://www.automationexercise.com"))
                && displayed(homeLogo);
    }
    public ProductsPage openProducts() { click(productsLink); return new ProductsPage(driver); }
    public ContactUsPage openContactUs() { click(contactLink); return new ContactUsPage(driver); }
}
