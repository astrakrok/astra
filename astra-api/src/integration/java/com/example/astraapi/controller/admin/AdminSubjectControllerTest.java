package com.example.astraapi.controller.admin;

import com.example.astraapi.config.BaseTest;
import com.example.astraapi.config.TestConfig;
import com.example.astraapi.dto.filter.AdminSubjectFilterDto;
import com.example.astraapi.dto.subject.RequestSubjectDto;
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
public class AdminSubjectControllerTest extends BaseTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json"})
    @ExpectedDataSet("datasets/expected/subjects/subject-creation.json")
    void shouldCreateSubject() {
        webClient.post()
                .uri("/api/v1/admin/subjects")
                .bodyValue(new RequestSubjectDto(101L, "   subject title "))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody()
                .jsonPath("$.id").exists();
    }

    @Test
    void shouldReturnBadRequestWhenCreateWithInvalidSubjectTitle() {
        webClient.post()
                .uri("/api/v1/admin/subjects")
                .bodyValue(new RequestSubjectDto(101L, "    111 "))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json"})
    @ExpectedDataSet("datasets/expected/subjects/subject-updating.json")
    void shouldUpdateSubjectTitle() {
        webClient.put()
                .uri("/api/v1/admin/subjects/103")
                .bodyValue(new RequestSubjectDto(101L, " Updated subject title    "))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnBadRequestWhenUpdateWithInvalidSubjectTitle() {
        webClient.put()
                .uri("/api/v1/admin/subjects/103")
                .bodyValue(new RequestSubjectDto(101L, "   111  "))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json"})
    void shouldFilterSubjectsByStepIdAndSearchTextIgnoreCase() {
        webClient.post()
                .uri("/api/v1/admin/subjects/details?pageSize=2&pageNumber=1")
                .bodyValue(new AdminSubjectFilterDto(103L, null, "exam"))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(1)
                .jsonPath("$.items[0].id").isEqualTo(116)
                .jsonPath("$.rows").isEqualTo(3)
                .jsonPath("$.pagesCount").isEqualTo(2);
    }

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json"})
    void shouldFilterSubjectsBySpecializationId() {
        webClient.post()
                .uri("/api/v1/admin/subjects/details?pageSize=2&pageNumber=1")
                .bodyValue(new AdminSubjectFilterDto(null, 108L, null))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(1)
                .jsonPath("$.items[0].id").isEqualTo(115)
                .jsonPath("$.rows").isEqualTo(3)
                .jsonPath("$.pagesCount").isEqualTo(2);
    }

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json"})
    void shouldReturnNoSubjectsWhenFilterByWrongSubjectIdAndSpecializationId() {
        webClient.post()
                .uri("/api/v1/admin/subjects/details?pageSize=2&pageNumber=1")
                .bodyValue(new AdminSubjectFilterDto(101L, 108L, null))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(0)
                .jsonPath("$.rows").isEqualTo(0)
                .jsonPath("$.pagesCount").isEqualTo(0);
    }
}
