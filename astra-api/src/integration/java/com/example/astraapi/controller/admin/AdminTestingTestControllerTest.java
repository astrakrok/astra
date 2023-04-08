package com.example.astraapi.controller.admin;

import com.example.astraapi.config.BaseTest;
import com.example.astraapi.config.TestConfig;
import com.example.astraapi.dto.test.RequestTestingDetailTestDto;
import com.example.astraapi.dto.test.RequestTestingTestDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@DBRider
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdminTestingTestControllerTest extends BaseTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json"})
    @ExpectedDataSet("datasets/expected/testings-tests/testing-test-creation.json")
    void shouldCreateTestingTestByTestingIdAndTestId() {
        webClient.post()
                .uri("/api/v1/admin/testings-tests")
                .bodyValue(new RequestTestingTestDto(107L, 103L))
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
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json"})
    void shouldReturnBadRequestWhenCreateByTestingIdAndTestIdTestWhichDoesNotBelongToSpecializationToWhichTestingDoes() {
        webClient.post()
                .uri("/api/v1/admin/testings-tests")
                .bodyValue(new RequestTestingTestDto(107L, 101L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.error.message").isEqualTo("You cannot add this test to this testing");
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json"})
    void shouldReturnBadRequestWhenCreateByTestingIdAndTestIdTestInDraftStatus() {
        webClient.post()
                .uri("/api/v1/admin/testings-tests")
                .bodyValue(new RequestTestingTestDto(107L, 104L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].type").isEqualTo("INVALID_STATUS");
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json"})
    void shouldReturnBadRequestWhenCreateByTestingIdAndTestIdTestingInActiveStatus() {
        webClient.post()
                .uri("/api/v1/admin/testings-tests")
                .bodyValue(new RequestTestingTestDto(110L, 103L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].type").isEqualTo("INVALID_STATUS");
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/testings-tests/dummy-testings-tests.json"})
    void shouldReturnBadRequestWhenCreateByTestingIdAndTestIdTestingTestAlreadyExists() {
        webClient.post()
                .uri("/api/v1/admin/testings-tests")
                .bodyValue(new RequestTestingTestDto(107L, 103L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].type").isEqualTo("CONFLICT");
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json"})
    @ExpectedDataSet("datasets/expected/testings-tests/testing-test-creation.json")
    void shouldCreateTestingTestByExamIdAndSpecializationIdAndTestId() {
        webClient.post()
                .uri("/api/v1/admin/testings-tests/details")
                .bodyValue(new RequestTestingDetailTestDto(103L, 109L, 103L))
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
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json"})
    void shouldReturnBadRequestWhenCreateByExamIdAndSpecializationIdAndTestIdTestWhichDoesNotBelongToSpecializationToWhichTestingDoes() {
        webClient.post()
                .uri("/api/v1/admin/testings-tests/details")
                .bodyValue(new RequestTestingDetailTestDto(103L, 109L, 101L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.error.message").isEqualTo("You cannot add this test to this testing");
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json"})
    void shouldReturnBadRequestWhenCreateByExamIdAndSpecializationIdAndTestIdTestInDraftStatus() {
        webClient.post()
                .uri("/api/v1/admin/testings-tests/details")
                .bodyValue(new RequestTestingDetailTestDto(103L, 109L, 104L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].type").isEqualTo("INVALID_STATUS");
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json"})
    void shouldReturnBadRequestWhenCreateByExamIdAndSpecializationIdAndTestIdTestingInActiveStatus() {
        webClient.post()
                .uri("/api/v1/admin/testings-tests/details")
                .bodyValue(new RequestTestingDetailTestDto(102L, 109L, 103L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].type").isEqualTo("INVALID_STATUS");
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/testings-tests/dummy-testings-tests.json"})
    void shouldReturnBadRequestWhenCreateByExamIdAndSpecializationIdAndTestIdTestingTestAlreadyExists() {
        webClient.post()
                .uri("/api/v1/admin/testings-tests/details")
                .bodyValue(new RequestTestingDetailTestDto(103L, 109L, 103L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].type").isEqualTo("CONFLICT");
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/testings-tests/dummy-testings-tests.json"})
    void shouldReturnBadRequestWhenDeletingTestFromActiveTestingByTestingIdAndTestId() {
        webClient.method(HttpMethod.DELETE)
                .uri("/api/v1/admin/testings-tests")
                .bodyValue(new RequestTestingTestDto(110L, 103L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].type").isEqualTo("INVALID_STATUS");
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/testings-tests/dummy-testings-tests.json"})
    @ExpectedDataSet("datasets/expected/testings-tests/testing-test-deletion.json")
    void shouldSuccessfullyDeleteTestingTestByTestingIdAndTestId() {
        webClient.method(HttpMethod.DELETE)
                .uri("/api/v1/admin/testings-tests")
                .bodyValue(new RequestTestingTestDto(107L, 103L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/testings-tests/dummy-testings-tests.json"})
    void shouldReturnBadRequestWhenDeletingTestFromActiveTestingByTestingTestId() {
        webClient.method(HttpMethod.DELETE)
                .uri("/api/v1/admin/testings-tests/111")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].type").isEqualTo("INVALID_STATUS");
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/testings-tests/dummy-testings-tests.json"})
    @ExpectedDataSet("datasets/expected/testings-tests/testing-test-deletion.json")
    void shouldSuccessfullyDeleteTestingTestByTestingTestId() {
        webClient.method(HttpMethod.DELETE)
                .uri("/api/v1/admin/testings-tests/110")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/tests/tests.json",
            "datasets/subjects/subjects.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/testings-tests/dummy-testings-tests.json"})
    void shouldReturnTestingsByTestId() {
        webClient.get()
                .uri("/api/v1/admin/testings-tests/103/testings")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[0].id").isEqualTo(110)
                .jsonPath("$[1].id").isEqualTo(105)
                .jsonPath("$[2].id").isEqualTo(107);
    }
}
