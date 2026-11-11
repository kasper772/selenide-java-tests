package io.github.kasper772.tests;

import io.github.kasper772.core.BaseTest;
import io.github.kasper772.pages.TablesPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;

/**
 * Expected order is computed from the table itself, then Selenide waits
 * until the column shows exactly that order (no sleeps, no stale reads).
 */
@Epic("Data")
@Feature("Sortable table")
@DisplayName("Data table")
class TablesTest extends BaseTest {

    private final TablesPage page = new TablesPage();

    @BeforeEach
    void openPage() {
        page.open();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    void has_four_rows() {
        page.column("last-name").shouldHave(size(4));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    void sorts_by_last_name_ascending_then_descending() {
        List<String> ascending = page.column("last-name").texts().stream()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();

        page.sortBy("last-name");
        page.column("last-name").shouldHave(exactTexts(ascending));

        page.sortBy("last-name");
        page.column("last-name").shouldHave(exactTexts(ascending.reversed()));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    void sorts_dues_as_money_not_as_text() {
        List<String> byAmount = page.column("dues").texts().stream()
                .sorted(Comparator.comparing(TablesPage::money))
                .toList();

        page.sortBy("dues");

        // As text "$100.00" would come before "$50.00"; as money it must be last.
        page.column("dues").shouldHave(exactTexts(byAmount));
        page.column("dues").last().shouldHave(text("$100.00"));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    void finds_a_person_by_email() {
        page.rowWithEmail("jdoe@hotmail.com")
                .shouldHave(text("Doe"), text("Jason"), text("$100.00"));
    }
}
