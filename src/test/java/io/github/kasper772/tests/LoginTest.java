package io.github.kasper772.tests;

import io.github.kasper772.core.BaseTest;
import io.github.kasper772.pages.LoginPage;
import io.github.kasper772.pages.SecureAreaPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static com.codeborne.selenide.Selenide.webdriver;

@Epic("Authentication")
@Feature("Login form")
@DisplayName("Login")
class LoginTest extends BaseTest {

    private final LoginPage loginPage = new LoginPage();

    @Test
    @Severity(SeverityLevel.BLOCKER)
    void valid_user_reaches_the_secure_area() {
        SecureAreaPage secure = loginPage.open().loginWithValidUser();

        webdriver().shouldHave(urlContaining("/secure"));
        secure.heading.shouldHave(text("Secure Area"));
        secure.flash.shouldHave(text("You logged into a secure area!"), cssClass("success"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    void logout_returns_to_the_login_page() {
        loginPage.open().loginWithValidUser().logout();

        webdriver().shouldHave(urlContaining("/login"));
        loginPage.flash.shouldHave(text("You logged out of the secure area!"));
    }

    @ParameterizedTest(name = "user \"{0}\" / password \"{1}\" → {2}")
    @CsvSource({
            "wronguser, SuperSecretPassword!, Your username is invalid!",
            "tomsmith,  wrongpassword,        Your password is invalid!",
            "'',        '',                   Your username is invalid!",
            "TOMSMITH,  SuperSecretPassword!, Your username is invalid!",
    })
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Invalid credentials are rejected")
    void invalid_credentials_are_rejected(String user, String password, String error) {
        loginPage.open().loginAs(user, password);

        loginPage.flash.shouldHave(text(error), cssClass("error"));
        webdriver().shouldHave(urlContaining("/login"));
    }
}
