package com.example.astra.util;

import org.openqa.selenium.By;

public class SelectorUtils {
    public static By byDataTestId(String dataTestId) {
        return new ByDataTestId(dataTestId);
    }

    public static class ByDataTestId extends By.ByXPath {
        private final String dataTestId;

        public ByDataTestId(String dataTestId) {
            super(".//*[@data-test-id='" + dataTestId + "']");
            this.dataTestId = dataTestId;
        }

        @Override
        public String toString() {
            return "by data-test-id: " + dataTestId;
        }
    }
}
