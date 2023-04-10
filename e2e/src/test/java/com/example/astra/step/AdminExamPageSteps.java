package com.example.astra.step;

import static com.codeborne.selenide.Selenide.open;

public class AdminExamPageSteps {
    private final CommonSteps commonSteps = new CommonSteps();

    public void openPage() {
        open("/admin/exams");
    }

    public void openCreateModal() {
        commonSteps.clickByDataTestId("exam-new-action");
    }

    public void clickDeleteExamButton(String title) {
        commonSteps.clickItemsByCssSelectorAndCondition(".ExamItem .delete", element -> title.equalsIgnoreCase(element.parent().$(".title").text()));
        commonSteps.delay(500);
    }

    public void typeExamTitle(String text) {
        commonSteps.typeTextByDataTestId("exam-input-field", text);
    }

    public void confirmExamCreation() {
        commonSteps.clickByDataTestId("exam-confirm-action");
    }

    public void confirmExamDeletion() {
        commonSteps.clickByCssSelector("[data-test-id='delete-exam-popup'] [data-test-id='confirm']");
    }

    public void assertExamsCount(int count) {
        commonSteps.assertItemsCountByCssSelector("[data-test-id='exam-list'] .ExamItem", count);
    }
}
