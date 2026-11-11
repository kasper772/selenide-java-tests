package io.github.kasper772.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

/** Example 1: the text is hidden and then shown. Example 2: the text is added to the page later. */
public class DynamicLoadingPage {
    private final SelenideElement start = $("#start button");
    public final SelenideElement loading = $("#loading");
    public final SelenideElement finish = $("#finish");

    @Step("Open dynamic loading example {example}")
    public DynamicLoadingPage open(int example) {
        Selenide.open("/dynamic_loading/" + example);
        return this;
    }

    @Step("Click Start")
    public DynamicLoadingPage start() {
        start.click();
        return this;
    }
}
