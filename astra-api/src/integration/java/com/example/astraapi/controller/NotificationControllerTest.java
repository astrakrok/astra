package com.example.astraapi.controller;

import com.example.astraapi.config.BaseTest;
import com.example.astraapi.config.TestConfig;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;

@DBRider
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class NotificationControllerTest extends BaseTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    void shouldSendMessage() {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("title", "Example title for message");
        bodyBuilder.part("text", "Example text for message")
                .contentType(MediaType.MULTIPART_FORM_DATA);

        webClient.post()
                .uri("/api/v1/notifications")
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnBadRequestWhenInvalidRequestBody() {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("title", "title");
        bodyBuilder.part("text", "text")
                .contentType(MediaType.MULTIPART_FORM_DATA);

        webClient.post()
                .uri("/api/v1/notifications")
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldValidateFileExtensionAndSuccessfullySendMessage() throws IOException {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        byte[] file = new ClassPathResource("files/dummy.png").getInputStream().readAllBytes();
        bodyBuilder.part("file", new ByteArrayResource(file)).header("Content-Disposition", "form-data; name=file; filename=dummy.png");
        bodyBuilder.part("title", "Example title", MediaType.TEXT_PLAIN);
        bodyBuilder.part("text", "Example text", MediaType.TEXT_PLAIN);

        webClient.post()
                .uri("/api/v1/notifications")
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnBadRequestWhenInvalidFileExtension() throws IOException {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        byte[] file = new ClassPathResource("files/dummy.txt").getInputStream().readAllBytes();
        bodyBuilder.part("title", "Example title", MediaType.TEXT_PLAIN);
        bodyBuilder.part("text", "Example text", MediaType.TEXT_PLAIN);
        bodyBuilder.part("file", new ByteArrayResource(file)).header("Content-Disposition", "form-data; name=file; filename=dummy.txt");

        webClient.post()
                .uri("/api/v1/notifications")
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldReturnBadRequestWhenBlankFilename() throws IOException {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        byte[] file = new ClassPathResource("files/dummy.png").getInputStream().readAllBytes();
        bodyBuilder.part("title", "Example title", MediaType.TEXT_PLAIN);
        bodyBuilder.part("text", "Example text", MediaType.TEXT_PLAIN);
        bodyBuilder.part("file", new ByteArrayResource(file)).header("Content-Disposition", "form-data; name=file; filename=");

        webClient.post()
                .uri("/api/v1/notifications")
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
