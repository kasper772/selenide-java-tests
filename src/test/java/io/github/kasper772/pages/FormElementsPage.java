package io.github.kasper772.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/** Small pages with standard form elements: checkboxes, dropdown, key presses, file upload. */
public class FormElementsPage {
    public final ElementsCollection checkboxes = $$("#checkboxes input[type=checkbox]");
    public final SelenideElement dropdown = $("#dropdown");
    public final SelenideElement keyTarget = $("#target");
    public final SelenideElement result = $("#result");
    public final SelenideElement uploadedFiles = $("#uploaded-files");
    public final SelenideElement heading = $("h3");

    @Step("Open {path}")
    public FormElementsPage open(String path) {
        Selenide.open(path);
        return this;
    }

    @Step("Upload a file")
    public FormElementsPage upload(File file) {
        $("#file-upload").uploadFile(file);
        $("#file-submit").click();
        return this;
    }
}
