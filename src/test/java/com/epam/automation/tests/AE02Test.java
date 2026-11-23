package com.epam.automation.tests;

import com.epam.automation.components.*;
import com.epam.automation.pages.*;
import com.epam.automation.support.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class AE02Test extends BaseTest {
    @Test
    public void addTwoProductsAndRemoveBlueTop() {
        verifyEmptyCartPrecondition();
        // 1 Navigate home (after independent precondition)
        home.openHome(); Assert.assertTrue(home.isDisplayed(), "Step 1: Home page");
        // 2 Open catalog
        ProductsPage products = home.openProducts(); Assert.assertTrue(products.isCatalogDisplayed(), "Step 2: Catalog");
        // 3 Find Blue Top
        ProductCard blueTop = products.findProduct(TestData.BLUE_TOP); Assert.assertTrue(blueTop.isVisible(), "Step 3: Blue Top card");
        // 4 Hover and add Blue Top
        CartConfirmation confirmation = blueTop.hoverAndAddToCart(); Assert.assertTrue(confirmation.isVisible(), "Step 4: Add confirmation");
        // 5 Continue shopping
        products = confirmation.continueShopping(); Assert.assertTrue(products.isCatalogDisplayed(), "Step 5: Catalog available");
        // 6 Locate and hover Men Tshirt
        ProductCard tshirt = products.findProduct(TestData.MEN_TSHIRT); tshirt.hover();
        Assert.assertTrue(tshirt.isOverlayAddControlAvailable(), "Step 6: Overlay add control");
        // 7 Add Men Tshirt
        confirmation = tshirt.addFromOverlay(); Assert.assertTrue(confirmation.isVisible(), "Step 7: Add confirmation");
        // 8 View cart
        CartPage cart = confirmation.openViewCart();
        Assert.assertTrue(cart.hasProduct(TestData.BLUE_TOP), "Step 8: Blue Top in cart");
        Assert.assertTrue(cart.hasProduct(TestData.MEN_TSHIRT), "Step 8: Men Tshirt in cart");
        // 9 Remove Blue Top
        cart.removeProduct(TestData.BLUE_TOP); Assert.assertFalse(cart.hasProduct(TestData.BLUE_TOP), "Step 9: Blue Top removed");
        // 10 Inspect remaining contents
        Assert.assertEquals(cart.getProductRows().size(), 1, "Step 10: One remaining row");
        CartRow remaining = cart.findRow(TestData.MEN_TSHIRT);
        Assert.assertEquals(remaining.getName(), TestData.MEN_TSHIRT, "Step 10: Remaining name");
        Assert.assertEquals(remaining.getQuantity(), 1, "Step 10: Remaining quantity");
    }
}
