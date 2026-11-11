package io.github.kasper772.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public static final String USERNAME = "tomsmith";
    public static final String PASSWORD = "SuperSecretPassword!";

    private final SelenideElement username = $("#username");
    private final SelenideElement password = $("#password");
    private final SelenideElement submit = $("#login button[type=submit]");
    public final SelenideElement flash = $("#flash");

    @Step("Open the login page")
    public LoginPage open() {
        Selenide.open("/login");
        return this;
    }

    @Step("Log in as \"{user}\"")
    public void loginAs(String user, String pass) {
        username.setValue(user);
        password.setValue(pass);
        submit.click();
    }

    @Step("Log in with valid credentials")
    public SecureAreaPage loginWithValidUser() {
        loginAs(USERNAME, PASSWORD);
        return new SecureAreaPage();
    }
}
