package com.example.astraapi.controller.admin;

import com.example.astraapi.config.BaseTest;
import com.example.astraapi.config.TestConfig;
import com.example.astraapi.dto.filter.AdminAvailableTestingTestsFilterDto;
import com.example.astraapi.dto.filter.AdminTestingTestsFilterDto;
import com.example.astraapi.dto.testing.RequestTestingDto;
import com.example.astraapi.dto.testing.TestingStatusDto;
import com.example.astraapi.meta.TestingStatus;
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

@DBRider
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdminTestingControllerTest extends BaseTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json"})
    @ExpectedDataSet("datasets/expected/testings/testing-creation.json")
    void shouldCreateTestingByExamIdAndSpecializationId() {
        webClient.post()
                .uri("/api/v1/admin/testings")
                .bodyValue(new RequestTestingDto(103L, 108L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody()
                .jsonPath("$.id").exists();
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/testings/active-and-draft-testings.json"})
    void shouldReturnTestingsWithSpecializationsByExamId() {
        webClient.get()
                .uri("/api/v1/admin/testings/exams/103")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(6)
                .jsonPath("$[0].id").isEqualTo(103)
                .jsonPath("$[1].id").isEqualTo(104)
                .jsonPath("$[2].id").isEqualTo(105)
                .jsonPath("$[3].id").isEqualTo(106)
                .jsonPath("$[4].id").isEqualTo(107)
                .jsonPath("$[5].id").isEqualTo(109);
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/tests/tests.json",
            "datasets/testings-tests/correct-testings-tests.json"})
    void shouldReturnTestingInfoById() {
        webClient.get()
                .uri("/api/v1/admin/testings/107/info")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.id").isEqualTo(107)
                .jsonPath("$.testsCount").isEqualTo(1)
                .jsonPath("$.exam.id").isEqualTo(103)
                .jsonPath("$.specialization.id").isEqualTo(109);
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/tests/tests.json",
            "datasets/testings-tests/dummy-testings-tests.json"})
    void shouldReturnTestingTestsById() {
        webClient.post()
                .uri("/api/v1/admin/testings/110/tests?pageSize=2&pageNumber=1")
                .bodyValue(new AdminTestingTestsFilterDto())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(1)
                .jsonPath("$.items[0].id").isEqualTo(108)
                .jsonPath("$.rows").isEqualTo(3)
                .jsonPath("$.pageSize").isEqualTo(2)
                .jsonPath("$.pagesCount").isEqualTo(2);
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/tests/tests.json",
            "datasets/testings-tests/dummy-testings-tests.json"})
    void shouldReturnEmptyPage() {
        webClient.post()
                .uri("/api/v1/admin/testings/120/tests?pageSize=2&pageNumber=1")
                .bodyValue(new AdminTestingTestsFilterDto())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(0)
                .jsonPath("$.testingId").isEqualTo(120)
                .jsonPath("$.rows").isEqualTo(0)
                .jsonPath("$.pageSize").isEqualTo(0)
                .jsonPath("$.pagesCount").isEqualTo(0);
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/tests/tests.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings-tests/correct-testings-tests.json"})
    void shouldReturnAvailableTestsForTesting() {
        webClient.post()
                .uri("/api/v1/admin/testings/105/tests/available?pageSize=1&pageNumber=1")
                .bodyValue(new AdminAvailableTestingTestsFilterDto("exam"))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(1)
                .jsonPath("$.items[0].id").isEqualTo(102)
                .jsonPath("$.rows").isEqualTo(2)
                .jsonPath("$.pageSize").isEqualTo(1)
                .jsonPath("$.pagesCount").isEqualTo(2);
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/testings/active-and-draft-testings.json"})
    void shouldReturnBadRequestWhenUpdateTestingStatusToActiveWithNoTests() {
        webClient.put()
                .uri("/api/v1/admin/testings/107/status")
                .bodyValue(new TestingStatusDto(TestingStatus.ACTIVE))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$[0].type").isEqualTo("EMPTY");
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/testings/active-and-draft-testings.json"})
    @ExpectedDataSet("datasets/expected/testings/testing-draft-status-updating.json")
    void shouldSuccessfullyUpdateTestingStatusToDraft() {
        webClient.put()
                .uri("/api/v1/admin/testings/101/status")
                .bodyValue(new TestingStatusDto(TestingStatus.DRAFT))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/tests/tests.json",
            "datasets/testings-tests/correct-testings-tests.json"})
    @ExpectedDataSet("datasets/expected/testings/testing-active-status-updating.json")
    void shouldUpdateTestingStatusToActive() {
        webClient.put()
                .uri("/api/v1/admin/testings/107/status")
                .bodyValue(new TestingStatusDto(TestingStatus.ACTIVE))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }
}
