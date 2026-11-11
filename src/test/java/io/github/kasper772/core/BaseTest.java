package io.github.kasper772.core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

/**
 * Common setup for every UI test.
 * Browser and headless mode come from system properties (selenide.browser, selenide.headless).
 */
public abstract class BaseTest {

    @BeforeAll
    static void configureSelenide() {
        Configuration.baseUrl = "https://the-internet.herokuapp.com";
        Configuration.browserSize = "1366x900";
        Configuration.timeout = 8_000;
        Configuration.pageLoadStrategy = "eager";

        // Every Selenide action becomes an Allure step; failures get a screenshot and the page source.
        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    @AfterEach
    void closeBrowser() {
        closeWebDriver();
    }
}
