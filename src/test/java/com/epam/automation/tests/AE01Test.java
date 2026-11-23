package com.epam.automation.tests;

import com.epam.automation.components.*;
import com.epam.automation.pages.*;
import com.epam.automation.support.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class AE01Test extends BaseTest {
    @Test
    public void searchBlueTopAndVerifyQuantityInCart() {
        // 1 Navigate to home
        home.openHome(); Assert.assertTrue(home.isDisplayed(), "Step 1: Home page");
        // 2 Open Products
        ProductsPage products = home.openProducts(); Assert.assertTrue(products.isCatalogDisplayed(), "Step 2: Catalog");
        // 3 Enter search term
        products.enterSearchTerm(TestData.BLUE_TOP); Assert.assertEquals(products.getSearchTerm(), TestData.BLUE_TOP, "Step 3: Search input");
        // 4 Submit search
        products.submitSearch(); Assert.assertTrue(products.isSearchedProductsHeadingDisplayed(), "Step 4: Search heading");
        // 5 Find product
        ProductCard product = products.findProduct(TestData.BLUE_TOP); Assert.assertTrue(product.isVisible(), "Step 5: Product card");
        // 6 Open details
        ProductDetailsPage details = product.openDetails(); Assert.assertEquals(details.getProductName(), TestData.BLUE_TOP, "Step 6: Product details");
        // 7 Set quantity
        details.setQuantity(4); Assert.assertEquals(details.getQuantity(), 4, "Step 7: Quantity field");
        // 8 Add to cart
        CartConfirmation confirmation = details.addToCart(); Assert.assertTrue(confirmation.isVisible(), "Step 8: Add confirmation");
        // 9 Open cart
        CartPage cart = confirmation.openViewCart(); Assert.assertTrue(cart.isDisplayed(), "Step 9: Cart view");
        // 10 Inspect matching row
        CartRow row = cart.findRow(TestData.BLUE_TOP);
        Assert.assertEquals(row.getName(), TestData.BLUE_TOP, "Step 10: Row name");
        Assert.assertEquals(row.getQuantity(), 4, "Step 10: Row quantity");
    }
}
