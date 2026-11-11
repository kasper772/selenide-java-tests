# Selenide · Java UI tests with Allure

[![Selenide tests](https://github.com/kasper772/selenide-java-tests/actions/workflows/tests.yml/badge.svg)](https://github.com/kasper772/selenide-java-tests/actions/workflows/tests.yml)
[![Allure report](https://img.shields.io/badge/Allure-report-orange?logo=qameta)](https://kasper772.github.io/selenide-java-tests/)
![Java 21](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Selenide](https://img.shields.io/badge/Selenide-7-4BAA4B)
![JUnit](https://img.shields.io/badge/JUnit-6-25A162?logo=junit5&logoColor=white)

UI tests in **Java + Selenide + JUnit** for [the-internet.herokuapp.com](https://the-internet.herokuapp.com),
a public playground of tricky web elements. Every push runs the suite in headless Chrome on
GitHub Actions and publishes an **Allure report** with steps, screenshots and history.

**[▶ Open the latest Allure report](https://kasper772.github.io/selenide-java-tests/)**

## What is covered

| Area | Tests |
|---|---|
| **Login** | valid login → secure area, logout, invalid credentials (data-driven, incl. case-sensitive username) |
| **Dynamic content** | element hidden then shown, element added later, checkbox removed/added, input enabled after an async call |
| **JavaScript alerts** | alert, confirm (accept / dismiss), prompt with typed text |
| **Data table** | row count, sort by last name (asc → desc), sort amounts as money, find a row by email |
| **Form elements** | checkboxes, dropdown (data-driven), special keys (data-driven), file upload |

**26 tests** in 5 classes, running in parallel.

## How it is built

```
src/test/java/io/github/kasper772/
├── core/BaseTest.java      Selenide config, Allure listener, browser cleanup
├── pages/                  Page Objects with @Step methods
│   ├── LoginPage, SecureAreaPage
│   ├── DynamicLoadingPage, DynamicControlsPage
│   ├── AlertsPage, TablesPage, FormElementsPage
└── tests/                  test classes, one per feature
src/test/resources/
├── junit-platform.properties   parallel run, readable test names
└── simplelogger.properties
```

Techniques worth a look:

- **No sleeps.** Selenide conditions wait on their own: `shouldHave(text(...))`, `should(disappear)`,
  `shouldHave(exactTexts(...))` for a whole column after sorting.
- **Page Objects with Allure steps.** `@Step` methods plus the `AllureSelenide` listener turn every
  action into a readable, nested step; failures attach a screenshot and the page source.
- **Allure labels.** `@Epic`, `@Feature` and `@Severity` group the report by product area and priority.
- **Data-driven tests.** `@ParameterizedTest` with `@CsvSource` / `@ValueSource`; every case is a
  separate, readable entry in the report.
- **Parallel execution.** JUnit runs test classes concurrently (one browser per thread).
- **Report history.** CI restores the previous report's history, so the Allure trend charts grow run by run.

## Run locally

Requirements: Java 21+, Maven, Chrome (or Firefox).

```bash
mvn test                                   # Chrome, visible window
mvn test -Dselenide.headless=true          # Chrome, headless
mvn test -Dselenide.browser=firefox        # another browser
mvn test -Dtest=LoginTest                  # one class

mvn allure:serve                           # open the Allure report
```

## CI

[GitHub Actions](.github/workflows/tests.yml) runs the tests on every push and pull request and
every Monday (to notice changes on the demo site), builds the Allure report with history and
environment info, and publishes it to GitHub Pages.
