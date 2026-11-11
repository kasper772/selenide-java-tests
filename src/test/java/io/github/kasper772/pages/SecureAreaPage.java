package io.github.kasper772.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class SecureAreaPage {
    public final SelenideElement heading = $("h2");
    public final SelenideElement flash = $("#flash");
    private final SelenideElement logout = $("a[href='/logout']");

    @Step("Log out")
    public LoginPage logout() {
        logout.click();
        return new LoginPage();
    }
}
