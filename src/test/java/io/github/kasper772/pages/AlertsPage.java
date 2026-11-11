package io.github.kasper772.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class AlertsPage {
    public final SelenideElement result = $("#result");

    @Step("Open JavaScript alerts")
    public AlertsPage open() {
        Selenide.open("/javascript_alerts");
        return this;
    }

    @Step("Click \"{button}\"")
    public void click(String button) {
        $(byText(button)).click();
    }
}
