package com.example.astraapi.controller.admin;

import com.example.astraapi.config.BaseTest;
import com.example.astraapi.config.TestConfig;
import com.example.astraapi.dto.PropertyValueDto;
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
public class AdminPropertyControllerTest extends BaseTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    @DataSet("datasets/properties/properties.json")
    void shouldReturnPropertiesMap() {
        webClient.get()
                .uri("/api/v1/admin/properties?names=adaptiveDescription,examinationThresholdPercentage")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.examinationThresholdPercentage").isEqualTo("value1")
                .jsonPath("$.adaptiveDescription").isEqualTo("value4");
    }

    @Test
    @DataSet("datasets/properties/properties.json")
    @ExpectedDataSet("datasets/expected/properties/property-updating.json")
    void shouldUpdateProperty() {
        webClient.put()
                .uri("/api/v1/admin/properties/examinationDescription")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(new PropertyValueDto("Updated property value"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }
}
