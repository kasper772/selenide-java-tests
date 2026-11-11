package io.github.kasper772.tests;

import io.github.kasper772.core.BaseTest;
import io.github.kasper772.pages.AlertsPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.confirm;
import static com.codeborne.selenide.Selenide.dismiss;
import static com.codeborne.selenide.Selenide.prompt;

@Epic("Browser features")
@Feature("JavaScript alerts")
@DisplayName("JavaScript alerts")
class AlertsTest extends BaseTest {

    private final AlertsPage page = new AlertsPage();

    @BeforeEach
    void openPage() {
        page.open();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    void alert_is_accepted() {
        page.click("Click for JS Alert");
        confirm("I am a JS Alert");

        page.result.shouldHave(text("You successfully clicked an alert"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    void confirm_can_be_accepted() {
        page.click("Click for JS Confirm");
        confirm("I am a JS Confirm");

        page.result.shouldHave(text("You clicked: Ok"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    void confirm_can_be_dismissed() {
        page.click("Click for JS Confirm");
        dismiss("I am a JS Confirm");

        page.result.shouldHave(text("You clicked: Cancel"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    void prompt_returns_the_typed_text() {
        page.click("Click for JS Prompt");
        prompt("I am a JS prompt", "Hello from Selenide");

        page.result.shouldHave(text("You entered: Hello from Selenide"));
    }
}
