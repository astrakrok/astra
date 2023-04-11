package com.example.astra.test;

import com.example.astra.config.BaseTest;
import com.example.astra.config.EnvironmentExtension;
import com.example.astra.step.AdminExamPageSteps;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@DBRider
@ExtendWith(EnvironmentExtension.class)
public class AdminExamPageTest extends BaseTest {
    @SuppressWarnings("unused")
    public ConnectionHolder connectionHolder = this::getConnection;

    private final AdminExamPageSteps steps = new AdminExamPageSteps();

    @Test
    void shouldCreateExam() {
        steps.openPage();
        steps.openCreateModal();
        steps.typeExamTitle("2018");
        steps.confirmExamCreation();
        steps.assertExamsCount(1);
    }

    @Test
    @DataSet("datasets/exams/exams.json")
    @ExpectedDataSet("datasets/expected/exams/exam-deletion.json")
    void shouldDeleteExam() {
        steps.openPage();
        steps.clickDeleteExamButton("Exam 2");
        steps.confirmExamDeletion();
    }
}
