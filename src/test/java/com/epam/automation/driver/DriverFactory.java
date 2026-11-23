package com.epam.automation.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;

public final class DriverFactory {
    private DriverFactory() { }
    public static WebDriver createDriver(String browser) {
        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito", "--disable-notifications");
                // Chromium host resolver prevents selected third-party ad hosts from resolving.
                // This is a test-environment option, not a guarantee that all ads disappear.
                boolean blockAds = Boolean.parseBoolean(
                        System.getProperty("ads.blocking.enabled", "true"));
                if (blockAds) {
                    options.addArguments("--host-resolver-rules=" +
                            "MAP googleads.g.doubleclick.net ~NOTFOUND, " +
                            "MAP doubleclick.net ~NOTFOUND, " +
                            "MAP *.doubleclick.net ~NOTFOUND, " +
                            "MAP pagead2.googlesyndication.com ~NOTFOUND, " +
                            "MAP *.googlesyndication.com ~NOTFOUND, " +
                            "MAP googleadservices.com ~NOTFOUND, " +
                            "MAP *.googleadservices.com ~NOTFOUND");
                }
                System.out.println("Chrome ad-host blocking enabled: " + blockAds);
                if (Boolean.getBoolean("headless")) options.addArguments("--headless=new");
                driver = new ChromeDriver(options);
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("-private");
                if (Boolean.getBoolean("headless")) options.addArguments("-headless");
                driver = new FirefoxDriver(options);
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().window().maximize();
        return driver;
    }
}
