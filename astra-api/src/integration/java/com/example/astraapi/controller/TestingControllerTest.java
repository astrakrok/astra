package com.example.astraapi.controller;

import com.example.astraapi.config.BaseTest;
import com.example.astraapi.config.TestConfig;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@DBRider
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestingControllerTest extends BaseTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    @DataSet("datasets/properties/properties.json")
    void shouldReturnTestingsDescriptions() {
        webClient.get()
                .uri("/api/v1/testings/description")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.trainingDescription").isEqualTo("value2")
                .jsonPath("$.examinationDescription").isEqualTo("value3")
                .jsonPath("$.adaptiveDescription").isEqualTo("value4");
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/tests/tests.json",
            "datasets/testings-tests/testings-tests.json"})
    void shouldReturnActiveTestings() {
        webClient.get()
                .uri("/api/v1/testings/available")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(5)
                .jsonPath("$[0].id").isEqualTo(101)
                .jsonPath("$[1].id").isEqualTo(111)
                .jsonPath("$[2].id").isEqualTo(110)
                .jsonPath("$[3].id").isEqualTo(106)
                .jsonPath("$[4].id").isEqualTo(109);
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/tests/tests.json"})
    void shouldReturnTestingByExamIdAndSpecializationId() {
        webClient.get()
                .uri("/api/v1/testings?examId=103&specializationId=101")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.id").isEqualTo(103);
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/tests/tests.json"})
    void shouldReturnNullWhenTestingByPassedExamIdAndSpecializationIdDoesNotExist() {
        webClient.get()
                .uri("/api/v1/testings?examId=200&specializationId=200")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.id").doesNotExist();
    }
}
