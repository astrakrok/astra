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
public class SpecializationControllerTest extends BaseTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json"})
    void shouldReturnDetailedListOfSpecializations() {
        webClient.get()
                .uri("/api/v1/specializations/details")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(9)
                .jsonPath("$[0].id").isEqualTo(101)
                .jsonPath("$[1].id").isEqualTo(102)
                .jsonPath("$[2].id").isEqualTo(103)
                .jsonPath("$[3].id").isEqualTo(104)
                .jsonPath("$[4].id").isEqualTo(105)
                .jsonPath("$[5].id").isEqualTo(106)
                .jsonPath("$[6].id").isEqualTo(107)
                .jsonPath("$[7].id").isEqualTo(108)
                .jsonPath("$[8].id").isEqualTo(109);
    }
}
