# Selenium WebDriver Test Automation

Automated UI tests for [Automation Exercise](https://www.automationexercise.com/) using Java, Selenium WebDriver, TestNG and Maven.

## Project overview

The project covers three independent end-to-end scenarios. Each test follows ten numbered steps and checks the expected result with TestNG assertions.

| Test | Scenario | Expected result |
| --- | --- | --- |
| `AE01Test` | Search for Blue Top and add four items to the cart | Blue Top appears in the cart with quantity 4 |
| `AE02Test` | Add Blue Top and Men Tshirt, then remove Blue Top | Only Men Tshirt remains, quantity 1 |
| `AE03Test` | Submit the Contact Us form with a text attachment | A confirmation alert appears and the success message is displayed |

## Technology stack

- Java 17
- Selenium WebDriver 4.21.0
- TestNG 7.10.2
- Maven
- Chrome (default) or Firefox

## Project structure

```text
src/test/
├── java/com/epam/automation/
│   ├── components/   # Product cards, cart rows and dialogs
│   ├── driver/       # Browser creation and configuration
│   ├── pages/        # Page Objects and shared page methods
│   ├── support/      # Test data and wait experiment
│   └── tests/        # BaseTest and three test classes
└── resources/
    ├── attachments/  # Contact form test file
    └── testng.xml     # TestNG suite
```

## Getting started

**Requirements:** JDK 17, Maven, and Chrome or Firefox. An internet connection is needed to access the test site and may be needed for Selenium Manager to obtain a browser driver.

1. Open the project folder in IntelliJ IDEA.
2. Set the Project SDK to JDK 17 and reload the Maven project.
3. Run the suite from the terminal:

```powershell
mvn clean test
```

You can also run `src/test/resources/testng.xml` directly in IntelliJ.

### Browser options

```powershell
# Firefox
mvn clean test '-Dbrowser=firefox'

# Chrome without a visible browser window
mvn clean test '-Dheadless=true'

# Chrome without ad-host blocking
mvn clean test '-Dads.blocking.enabled=false'
```

Chrome blocks DNS resolution for selected advertising domains by default. This helps reduce third-party overlays on the public test site, but it does not guarantee that all ads will disappear. The setting can be disabled to compare behavior.

## Framework design

The tests use the **Page Object Model**. Page classes inherit shared navigation and wait methods from `AbstractPage`; reusable page elements are represented by separate components. The test classes contain the assertions, while page classes handle browser interactions.

Each test starts a fresh WebDriver session. The baseline uses an implicit wait of zero and explicit waits for elements, dialogs and alerts. `WaitExperiment` is a separate diagnostic utility and is not part of the three acceptance tests.

## Test results

The last verified user-run suite (R5) completed successfully on **23 November 2026**, using Windows 10, Java 17 and Chrome 157:

```text
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Total Maven build time: **1 min 12 sec**. Ad-host blocking was enabled. These results apply to R5 only. R7 contains source-level quality changes and has not yet been executed.

Maven writes detailed results to `target/surefire-reports/`. Failed tests also produce screenshots in `target/failure-screenshots/`.

## Notes

- The test site is public and can be affected by advertising overlays or temporary server overload.
- The Contact Us test uses a file from `src/test/resources/attachments/` and checks the native confirmation dialog before accepting it.
- R7 requires a fresh `mvn clean test` run and separate `WaitExperiment` measurements before final sign-off.
- A focused independent code re-review is required before submission.

## R7.1 advertisement-handling experiment

This variant removes `AdvertisementDialog` calls and its class while retaining Chrome ad-host blocking, consent handling, and existing click retries. It is an **unverified experiment**, not a confirmed stable release. Run `mvn clean test` three times and compare with R7; keep the original R7 archive as a rollback. A successful run does not guarantee all ads are blocked.
