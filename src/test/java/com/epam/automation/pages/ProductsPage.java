package com.epam.automation.pages;

import com.epam.automation.components.ProductCard;
import org.openqa.selenium.*;

public final class ProductsPage extends AbstractPage {
    private final By catalog = By.cssSelector(".features_items");
    private final By search = By.id("search_product");
    private final By submit = By.id("submit_search");
    private final By searchedHeading = By.xpath("//h2[contains(@class,'title') and normalize-space()='Searched Products']");
    public ProductsPage(WebDriver driver) { super(driver); }
    public boolean isCatalogDisplayed() { return displayed(catalog); }
    public void enterSearchTerm(String term) { type(search, term); }
    public String getSearchTerm() { return value(search); }
    public void submitSearch() { click(submit); }
    public boolean isSearchedProductsHeadingDisplayed() { return displayed(searchedHeading); }
    public ProductCard findProduct(String name) { return new ProductCard(driver, name); }
}
