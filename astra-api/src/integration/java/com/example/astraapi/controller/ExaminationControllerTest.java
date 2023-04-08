package com.example.astraapi.controller;

import com.example.astraapi.config.BaseTest;
import com.example.astraapi.config.TestConfig;
import com.example.astraapi.dto.examination.ExaminationAnswerDto;
import com.example.astraapi.dto.examination.ExaminationSearchDto;
import com.example.astraapi.dto.examination.ExaminationStateDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@DBRider
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExaminationControllerTest extends BaseTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    @DataSet({
            "datasets/users/users.json",
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json",
            "datasets/tests/tests.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/tests-variants/tests-variants.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/testings-tests/correct-testings-tests.json"})
    void shouldStartExamination() {
        ExaminationStateDto examination = webClient.post()
                .uri("/api/v1/examinations")
                .bodyValue(new ExaminationSearchDto(107L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(ExaminationStateDto.class)
                .returnResult()
                .getResponseBody();

        LocalDateTime utcTime = LocalDateTime.now()
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime();

        assertNotNull(examination);
        assertEquals(1, examination.getTests().size());
        assertTrue(utcTime.plusMinutes(150).until(examination.getFinishedAt(), ChronoUnit.MINUTES) < 1);
    }

    @Test
    @DataSet({
            "datasets/users/users.json",
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json",
            "datasets/tests/tests.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/tests-variants/tests-variants.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/testings-tests/correct-testings-tests.json",
            "datasets/examinations/examinations.json",
            "datasets/examinations-answers/examinations-answers.json"})
    @ExpectedDataSet("datasets/expected/examinations-answers/examinations-answers-updating.json")
    void shouldUpdateExaminationAnswer() {
        webClient.put()
                .uri("/api/v1/examinations/101")
                .bodyValue(new ExaminationAnswerDto(101L, 101L, 104L, 110L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    @DataSet({
            "datasets/users/users.json",
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json",
            "datasets/tests/tests.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/tests-variants/tests-variants.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/testings-tests/correct-testings-tests.json",
            "datasets/examinations/examinations.json",
            "datasets/examinations-answers/examinations-answers.json"})
    void shouldReturnBadRequestWhenTryingToUpdateExpiredExaminationAnswer() {
        webClient.put()
                .uri("/api/v1/examinations/102")
                .bodyValue(new ExaminationAnswerDto(102L, 102L, 104L, 110L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
