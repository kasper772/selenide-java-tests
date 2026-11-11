package io.github.kasper772.tests;

import io.github.kasper772.core.BaseTest;
import io.github.kasper772.pages.DynamicControlsPage;
import io.github.kasper772.pages.DynamicLoadingPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

/** Shows Selenide's smart waits: no sleeps, every check waits for the condition. */
@Epic("Dynamic content")
@DisplayName("Dynamic content")
class DynamicContentTest extends BaseTest {

    @ParameterizedTest(name = "example {0}")
    @ValueSource(ints = {1, 2})
    @Feature("Dynamic loading")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Text appears after loading")
    void text_appears_after_loading(int example) {
        DynamicLoadingPage page = new DynamicLoadingPage().open(example).start();

        page.loading.should(disappear, Duration.ofSeconds(15));
        page.finish.shouldBe(visible).shouldHave(text("Hello World!"));
    }

    @Test
    @Feature("Dynamic controls")
    @Severity(SeverityLevel.NORMAL)
    void checkbox_can_be_removed_and_added_back() {
        DynamicControlsPage page = new DynamicControlsPage().open();

        page.toggleCheckbox();
        page.message.shouldHave(text("It's gone!"));
        page.checkbox.shouldNot(exist);

        page.toggleCheckbox();
        page.message.shouldHave(text("It's back!"));
        page.checkbox.should(exist);
    }

    @Test
    @Feature("Dynamic controls")
    @Severity(SeverityLevel.NORMAL)
    void input_becomes_editable_after_enabling() {
        DynamicControlsPage page = new DynamicControlsPage().open();
        page.input.shouldBe(disabled);

        page.toggleInput();

        page.message.shouldHave(text("It's enabled!"));
        page.input.shouldBe(enabled).setValue("Selenide waits for me");
        page.input.shouldHave(com.codeborne.selenide.Condition.value("Selenide waits for me"));
    }
}
