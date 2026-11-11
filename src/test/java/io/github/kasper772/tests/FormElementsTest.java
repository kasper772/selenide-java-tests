package io.github.kasper772.tests;

import io.github.kasper772.core.BaseTest;
import io.github.kasper772.pages.FormElementsPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Keys;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.codeborne.selenide.Condition.checked;
import static com.codeborne.selenide.Condition.text;

@Epic("Form elements")
@DisplayName("Form elements")
class FormElementsTest extends BaseTest {

    private final FormElementsPage page = new FormElementsPage();

    @Test
    @Feature("Checkboxes")
    @Severity(SeverityLevel.NORMAL)
    void checkboxes_can_be_toggled() {
        page.open("/checkboxes");
        page.checkboxes.get(0).shouldNotBe(checked);
        page.checkboxes.get(1).shouldBe(checked);

        page.checkboxes.get(0).click();
        page.checkboxes.get(1).click();

        page.checkboxes.get(0).shouldBe(checked);
        page.checkboxes.get(1).shouldNotBe(checked);
    }

    @ParameterizedTest(name = "\"{0}\"")
    @CsvSource({"Option 1, 1", "Option 2, 2"})
    @Feature("Dropdown")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Dropdown option can be selected")
    void dropdown_option_can_be_selected(String option, String value) {
        page.open("/dropdown");

        page.dropdown.selectOption(option);

        page.dropdown.getSelectedOption().shouldHave(text(option));
        page.dropdown.getSelectedOption().shouldHave(com.codeborne.selenide.Condition.value(value));
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({"TAB", "ESCAPE", "SPACE", "BACK_SPACE"})
    @Feature("Keyboard")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Special key is recognised")
    void special_key_is_recognised(Keys key) {
        page.open("/key_presses");

        page.keyTarget.sendKeys(key);

        page.result.shouldHave(text("You entered: " + key.name()));
    }

    @Test
    @Feature("File upload")
    @Severity(SeverityLevel.CRITICAL)
    void file_is_uploaded(@TempDir Path dir) throws IOException {
        Path file = Files.writeString(dir.resolve("selenide-upload.txt"), "Uploaded by an automated test");

        page.open("/upload").upload(file.toFile());

        page.heading.shouldHave(text("File Uploaded!"));
        page.uploadedFiles.shouldHave(text("selenide-upload.txt"));
    }
}
