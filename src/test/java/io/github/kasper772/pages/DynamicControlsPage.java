package io.github.kasper772.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class DynamicControlsPage {
    public final SelenideElement checkbox = $("#checkbox-example input[type=checkbox]");
    private final SelenideElement checkboxButton = $("#checkbox-example button");
    public final SelenideElement input = $("#input-example input");
    private final SelenideElement inputButton = $("#input-example button");
    public final SelenideElement message = $("#message");

    @Step("Open dynamic controls")
    public DynamicControlsPage open() {
        Selenide.open("/dynamic_controls");
        return this;
    }

    @Step("Click Remove / Add for the checkbox")
    public DynamicControlsPage toggleCheckbox() {
        checkboxButton.click();
        return this;
    }

    @Step("Click Enable / Disable for the input")
    public DynamicControlsPage toggleInput() {
        inputButton.click();
        return this;
    }
}
