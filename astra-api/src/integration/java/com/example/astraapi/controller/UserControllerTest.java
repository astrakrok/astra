package com.example.astraapi.controller;

import com.example.astraapi.config.BaseTest;
import com.example.astraapi.config.TestConfig;
import com.example.astraapi.dto.UpdateUserDto;
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
public class UserControllerTest extends BaseTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    void shouldReturnCurrentUser() {
        webClient.get()
                .uri("/api/v1/users/current")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.id").isEqualTo(101)
                .jsonPath("$.email").isEqualTo("mock@email.com");
    }

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/users/users.json"})
    @ExpectedDataSet("datasets/expected/users/user-updating.json")
    void shouldUpdateCurrentUser() {
        webClient.put()
                .uri("/api/v1/users")
                .bodyValue(new UpdateUserDto("Mock updated", "Mockovich updated", 4, "School updated", 103L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/users/users.json"})
    void shouldReturnBadRequestOnUpdateWhenInalidUserDate() {
        webClient.put()
                .uri("/api/v1/users")
                .bodyValue(new UpdateUserDto(" 1    ", "Mockovich updated", 4, null, 103L))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
