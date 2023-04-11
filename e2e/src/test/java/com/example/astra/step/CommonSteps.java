package com.example.astra.step;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.util.function.Predicate;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.example.astra.util.SelectorUtils.byDataTestId;

public class CommonSteps {
    public void typeTextByDataTestId(String dataTestId, String text) {
        $(byDataTestId(dataTestId)).sendKeys(text);
    }

    public void clickByDataTestId(String dataTestId) {
        $(byDataTestId(dataTestId)).click();
    }

    public void clickByCssSelector(String cssSelector) {
        $(byCssSelector(cssSelector)).click();
    }

    public void clickItemsByCssSelectorAndCondition(String cssSelector, Predicate<SelenideElement> predicate) {
        $$(byCssSelector(cssSelector)).stream()
                .filter(predicate)
                .forEach(SelenideElement::click);
    }

    public void assertItemsCountByCssSelector(String cssSelector, int count) {
        $$(byCssSelector(cssSelector)).shouldHave(size(count));
    }

    public void delay(long milliseconds) {
        Selenide.sleep(milliseconds);
    }
}
