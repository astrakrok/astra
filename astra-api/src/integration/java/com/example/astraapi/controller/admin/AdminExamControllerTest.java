package com.example.astraapi.controller.admin;

import com.example.astraapi.config.BaseTest;
import com.example.astraapi.config.TestConfig;
import com.example.astraapi.dto.exam.RequestExamDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@DBRider
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdminExamControllerTest extends BaseTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    @ExpectedDataSet(value = "datasets/expected/exams/exam-creation.json")
    void shouldCreateExam() {
        webClient.post()
                .uri("/api/v1/admin/exams")
                .bodyValue(new RequestExamDto(" Exam is created     "))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody()
                .jsonPath("$.id").exists();
    }

    @Test
    void shouldReturnBadRequestOnIncorrectExamTitleLength() {
        webClient.post()
                .uri("/api/v1/admin/exams")
                .bodyValue(new RequestExamDto(" Exa     "))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DataSet("datasets/exams/exams.json")
    @ExpectedDataSet(value = "datasets/expected/exams/exam-deletion.json")
    void shouldDeleteExamById() {
        webClient.delete()
                .uri("/api/v1/admin/exams/102")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    @DataSet("datasets/exams/exams.json")
    @ExpectedDataSet(value = "datasets/expected/exams/exam-updating.json")
    void shouldUpdateExamById() {
        webClient.put()
                .uri("/api/v1/admin/exams/102")
                .bodyValue(new RequestExamDto("   Updated exam title "))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    @DataSet("datasets/exams/exams.json")
    @ExpectedDataSet(value = "datasets/expected/exams/exams.json")
    void shouldNotUpdateExamByIdWhenInvalidRequestBody() {
        webClient.put()
                .uri("/api/v1/admin/exams/102")
                .bodyValue(new RequestExamDto(" xam  "))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/testings/testings.json"})
    void shouldReturnNotSelectedSpecializationsForExam() {
        webClient.get()
                .uri("/api/v1/admin/exams/103/specializations/available")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(4)
                .jsonPath("$[0].id").isEqualTo(108)
                .jsonPath("$[1].id").isEqualTo(106)
                .jsonPath("$[2].id").isEqualTo(105)
                .jsonPath("$[3].id").isEqualTo(103);
    }
}
