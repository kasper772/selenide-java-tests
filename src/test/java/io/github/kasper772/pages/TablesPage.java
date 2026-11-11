package io.github.kasper772.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.math.BigDecimal;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/** Works with the second table on /tables: its cells have classes (last-name, dues, email...). */
public class TablesPage {
    private static final String TABLE = "#table2";

    @Step("Open data tables")
    public TablesPage open() {
        Selenide.open("/tables");
        return this;
    }

    @Step("Sort by \"{column}\"")
    public TablesPage sortBy(String column) {
        // tablesorter listens on the header cell, not on the span inside it
        $(TABLE + " thead ." + column).closest("th").click();
        return this;
    }

    public ElementsCollection column(String column) {
        return $$(TABLE + " tbody td." + column);
    }

    /** Turns "$51.00" into 51.00, so amounts are compared as money and not as text. */
    public static BigDecimal money(String due) {
        return new BigDecimal(due.replace("$", ""));
    }

    public SelenideElement rowWithEmail(String email) {
        return $$(TABLE + " tbody tr").findBy(text(email));
    }
}
