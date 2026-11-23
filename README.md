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

## Test results (R7.1)

The R7.1 suite was executed locally on 23 November 2026 using Chrome 157 and Java 17. The available Maven console evidence contains four sequential runs:

| Run | TestNG result | Maven result |
| --- | --- | --- |
| 1 | 3 passed, 0 failed | BUILD SUCCESS |
| 2 | 3 passed, 0 failed | BUILD SUCCESS |
| 3 | 2 passed, 1 failed (AE03 step 2: contact form not displayed) | BUILD FAILURE |
| 4 | 3 passed, 0 failed | BUILD SUCCESS |

The last run's Surefire XML/TXT reports confirm three tests, zero failures, zero errors and zero skipped. Earlier runs are supported by console logs only. The intermittent AE03 failure remains a known stability risk; the root cause was not established. Execution evidence is retained separately from this source repository.

### Wait experiment (AC-05)

The standalone `WaitExperiment` utility compared two-second waits on a deliberately missing element, using separate browser sessions:

| Mode | Measured time |
| --- | ---: |
| Implicit-only | 2.018 s |
| Explicit-only | 2.109 s |

These are single measurements, not a performance benchmark. The normal acceptance suite uses explicit waits with implicit wait set to zero. Chrome 157 emitted a non-blocking CDP compatibility warning with Selenium 4.21.0.

## Notes

The site is a public external test application, so availability and overlays may affect repeatability. Chrome ad-host blocking is enabled by default for the tested configuration. The source repository intentionally excludes local reports, screenshots, build output and internal project coordination records.
