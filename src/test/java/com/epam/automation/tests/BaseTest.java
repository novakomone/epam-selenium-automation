package com.epam.automation.tests;

import com.epam.automation.driver.DriverFactory;
import com.epam.automation.pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.testng.annotations.*;

public abstract class BaseTest {
    private WebDriver driver;
    protected HomePage home;
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.createDriver(System.getProperty("browser", "chrome"));
        home = new HomePage(driver);
    }
    protected void verifyEmptyCartPrecondition() {
        CartPage cart = new CartPage(driver).openCartForPrecondition();
        Assert.assertTrue(cart.isEmpty(), "Precondition violated: expected an empty cart in a fresh browser session; found " + cart.getRowCount() + " rows");
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null && !result.isSuccess()) {
            try {
                Path directory = Path.of("target", "failure-screenshots");
                Files.createDirectories(directory);
                Files.copy(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath(),
                        directory.resolve(result.getMethod().getMethodName() + ".png"),
                        StandardCopyOption.REPLACE_EXISTING);
                System.err.println("FAILED URL: " + driver.getCurrentUrl());
                System.err.println("Screenshot saved in: " + directory.toAbsolutePath());
            } catch (Exception diagnosticFailure) {
                System.err.println("Failure diagnostics unavailable: " + diagnosticFailure);
            }
        }
        if (driver != null) { try { driver.quit(); } finally { driver = null; } }
    }
}
