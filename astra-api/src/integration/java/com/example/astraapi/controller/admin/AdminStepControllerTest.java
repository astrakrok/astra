package com.example.astraapi.controller.admin;

import com.example.astraapi.config.BaseTest;
import com.example.astraapi.config.TestConfig;
import com.example.astraapi.dto.step.StepDto;
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
public class AdminStepControllerTest extends BaseTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    @ExpectedDataSet("datasets/expected/steps/step-creation.json")
    void shouldCreateStep() {
        webClient.post()
                .uri("/api/v1/admin/steps")
                .bodyValue(new StepDto(null, "   step "))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody()
                .jsonPath("$.id").exists();
    }

    @Test
    void shouldReturnBadRequestWhenInvalidRequestBody() {
        webClient.post()
                .uri("/api/v1/admin/steps")
                .bodyValue(new StepDto(null, "   1 "))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
