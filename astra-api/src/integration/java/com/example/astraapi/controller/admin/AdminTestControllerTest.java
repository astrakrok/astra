package com.example.astraapi.controller.admin;

import com.example.astraapi.config.BaseTest;
import com.example.astraapi.config.TestConfig;
import com.example.astraapi.dto.filter.AdminTestFilterDto;
import com.example.astraapi.dto.test.RequestTestDto;
import com.example.astraapi.dto.test.variant.TestVariantDto;
import com.example.astraapi.meta.TestStatus;
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

import java.util.List;
import java.util.Set;

@DBRider
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdminTestControllerTest extends BaseTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json"})
    @ExpectedDataSet({
            "datasets/expected/tests/active-test-creation.json",
            "datasets/expected/variants/active-variants.json",
            "datasets/expected/tests-subjects/tests-subjects.json"})
    void shouldSaveTestAsActive() {
        webClient.post()
                .uri("/api/v1/admin/tests")
                .bodyValue(new RequestTestDto(
                        null,
                        "Test Question",
                        "Test Comment",
                        null,
                        null,
                        List.of(
                                new TestVariantDto(null, null, "Test Variant Title 1", null, "Test Variant Explanation 1", null, true),
                                new TestVariantDto(null, null, "Test Variant Title 2", null, "Test Variant Explanation 2", null, false)),
                        Set.of(101L, 102L, 103L)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody()
                .jsonPath("$.id").exists();
    }

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json"})
    void shouldReturnBadRequestWhenEmptyFields() {
        webClient.post()
                .uri("/api/v1/admin/tests")
                .bodyValue(new RequestTestDto(
                        null,
                        "Test",
                        "Test",
                        null,
                        null,
                        List.of(
                                new TestVariantDto(null, null, "Test Variant Title 1", null, "Test Variant Explanation 1", null, true),
                                new TestVariantDto(null, null, "Test Variant Title 2", null, "Test Variant Explanation 2", null, false)),
                        Set.of(101L, 102L, 103L)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json"})
    void shouldReturnBadRequestWhenIncorrectAmountOfCorrectVariants() {
        webClient.post()
                .uri("/api/v1/admin/tests")
                .bodyValue(new RequestTestDto(
                        null,
                        "Test Question",
                        "Test Comment",
                        null,
                        null,
                        List.of(
                                new TestVariantDto(null, null, "Test Variant Title 1", null, "Test Variant Explanation 1", null, true),
                                new TestVariantDto(null, null, "Test Variant Title 2", null, "Test Variant Explanation 2", null, true)),
                        Set.of(101L, 102L, 103L)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json"})
    void shouldReturnBadRequestWhenNoTestsSubjects() {
        webClient.post()
                .uri("/api/v1/admin/tests")
                .bodyValue(new RequestTestDto(
                        null,
                        "Test Question",
                        "Test Comment",
                        null,
                        null,
                        List.of(
                                new TestVariantDto(null, null, "Test Variant Title 1", null, "Test Variant Explanation 1", null, false),
                                new TestVariantDto(null, null, "Test Variant Title 2", null, "Test Variant Explanation 2", null, true)),
                        Set.of()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json"})
    @ExpectedDataSet(value = {
            "datasets/expected/tests/draft-test-creation.json",
            "datasets/expected/variants/draft-variants.json"}, ignoreCols = "id")
    void shouldCreateDraftTest() {
        webClient.post()
                .uri("/api/v1/admin/tests/draft")
                .bodyValue(new RequestTestDto(
                        null,
                        "Test",
                        "Test",
                        null,
                        null,
                        List.of(
                                new TestVariantDto(null, null, "Title 1", null, "Explanation 1", null, false),
                                new TestVariantDto(null, null, "Title 2", null, "Explanation 2", null, false)),
                        Set.of()))
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
            "datasets/subjects/subjects.json",
            "datasets/tests/tests.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/testings-tests/correct-testings-tests.json",
            "datasets/tests-variants/tests-variants.json"})
    @ExpectedDataSet({
            "datasets/expected/tests/active-test-updating.json",
            "datasets/expected/variants/active-test-updating-variants.json",
            "datasets/expected/tests-subjects/active-test-updating-subjects.json"})
    void shouldUpdateDraftTestToActive() {
        webClient.put()
                .uri("/api/v1/admin/tests/104")
                .bodyValue(new RequestTestDto(
                        104L,
                        "Updated test question",
                        "Updated test comment",
                        null,
                        null,
                        List.of(
                                new TestVariantDto(101L, 104L, "example title 1(updated)", null, "example explanation 1(updated)", null, false),
                                new TestVariantDto(102L, 104L, "example title 2(updated)", null, "example explanation 2(updated)", null, false),
                                new TestVariantDto(null, 104L, "example title(new)", null, "example explanation(new)", null, true)),
                        Set.of(104L, 105L, 106L)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.id").exists();
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json",
            "datasets/tests/tests.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/testings-tests/correct-testings-tests.json",
            "datasets/tests-variants/tests-variants.json"})
    void shouldReturnBadRequestOnRedundantTestingError() {
        webClient.put()
                .uri("/api/v1/admin/tests/104")
                .bodyValue(new RequestTestDto(
                        104L,
                        "Updated test question",
                        "Updated test comment",
                        null,
                        null,
                        List.of(
                                new TestVariantDto(101L, 104L, "example title 1(updated)", null, "example explanation 1(updated)", null, false),
                                new TestVariantDto(102L, 104L, "example title 2(updated)", null, "example explanation 2(updated)", null, false),
                                new TestVariantDto(null, 104L, "example title(new)", null, "example explanation(new)", null, true)),
                        Set.of(106L)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$[0].type").isEqualTo("REDUNDANT_TESTINGS");
    }

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json",
            "datasets/tests/tests.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/tests-variants/tests-variants.json"})
    @ExpectedDataSet({
            "datasets/expected/tests/draft-test-updating.json",
            "datasets/expected/variants/draft-test-updating-variants.json",
            "datasets/expected/tests-subjects/draft-test-updating-subjects.json"})
    void shouldUpdateDraftTestToDraft() {
        webClient.put()
                .uri("/api/v1/admin/tests/104/draft")
                .bodyValue(new RequestTestDto(
                        104L,
                        "draft",
                        "draft",
                        null,
                        null,
                        List.of(),
                        Set.of()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.id").exists();
    }

    @Test
    @DataSet("datasets/tests/tests.json")
    void shouldReturnBadRequestWhenUpdateActiveTestAsDraft() {
        webClient.put()
                .uri("/api/v1/admin/tests/101/draft")
                .bodyValue(new RequestTestDto(
                        104L,
                        "draft",
                        "draft",
                        null,
                        null,
                        List.of(),
                        Set.of()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$[0].type").isEqualTo("INVALID_STATUS");
    }

    @Test
    @DataSet("datasets/tests/tests.json")
    void shouldFilterTests() {
        webClient.post()
                .uri("/api/v1/admin/tests/filter?pageSize=2&pageNumber=1")
                .bodyValue(new AdminTestFilterDto("exam", TestStatus.ACTIVE, null))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(2)
                .jsonPath("$.rows").isEqualTo(5)
                .jsonPath("$.pageSize").isEqualTo(2)
                .jsonPath("$.pagesCount").isEqualTo(3);
    }

    @Test
    @DataSet({
            "datasets/exams/exams.json",
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json",
            "datasets/tests/tests.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/testings/active-and-draft-testings.json",
            "datasets/testings-tests/correct-testings-tests.json",
            "datasets/tests-variants/tests-variants.json"})
    void shouldReturnTestById(){
        webClient.get()
                .uri("/api/v1/admin/tests/104")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.id").isEqualTo(104)
                .jsonPath("$.variants.length()").isEqualTo(3)
                .jsonPath("$.subjects.length()").isEqualTo(2)
                .jsonPath("$.testings.length()").isEqualTo(1);
    }

    @Test
    @DataSet("datasets/tests/tests.json")
    void shouldReturnBadRequestWhenDeleteActiveTest() {
        webClient.delete()
                .uri("/api/v1/admin/tests/101")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$[0].type").isEqualTo("INVALID_STATUS");
    }

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json",
            "datasets/tests/tests.json",
            "datasets/tests-subjects/tests-subjects.json",
            "datasets/tests-variants/tests-variants.json"})
    @ExpectedDataSet({
            "datasets/expected/tests/draft-test-deletion.json",
            "datasets/expected/variants/draft-test-deletion-variants.json",
            "datasets/expected/tests-subjects/draft-test-deletion-subjects.json"})
    void shouldDeleteDraftTest() {
        webClient.delete()
                .uri("/api/v1/admin/tests/104")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }
}
