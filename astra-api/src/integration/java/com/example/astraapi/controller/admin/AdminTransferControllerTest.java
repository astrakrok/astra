package com.example.astraapi.controller.admin;

import com.example.astraapi.config.BaseTest;
import com.example.astraapi.config.TestConfig;
import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.dto.filter.AdminImportTestFilterDto;
import com.example.astraapi.dto.importing.WebImportDto;
import com.example.astraapi.meta.FileType;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

@DBRider
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdminTransferControllerTest extends BaseTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    @DataSet({
            "datasets/steps/transfer-steps.json",
            "datasets/specializations/transfer-specializations.json",
            "datasets/subjects/transfer-subjects.json"})
    @ExpectedDataSet({
            "datasets/expected/imports/csv-import-creation.json",
            "datasets/expected/tests/file-import-creation-tests.json",
            "datasets/expected/variants/file-import-creation-variants.json",
            "datasets/expected/tests-subjects/file-import-creation-tests-subjects.json"})
    void shouldImportTestsFromCsvFile() throws IOException {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        byte[] file = new ClassPathResource("transfer/import/import.csv").getInputStream().readAllBytes();
        bodyBuilder.part("file", new ByteArrayResource(file)).header("Content-Disposition", "form-data; name=file; filename=import.csv");
        bodyBuilder.part("title", "CSV Import", MediaType.TEXT_PLAIN);

        webClient.post()
                .uri("/api/v1/admin/transfer/import/file")
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnInternalServerErrorWhenIOExceptionWhenImportCsvFile() throws IOException {
        try (MockedConstruction<CSVParser> ignored = mockConstruction(
                CSVParser.class,
                withSettings().defaultAnswer(invocation -> {
                    throw new IOException();
                })
        )) {
            MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
            byte[] file = new ClassPathResource("transfer/import/import.csv").getInputStream().readAllBytes();
            bodyBuilder.part("file", new ByteArrayResource(file)).header("Content-Disposition", "form-data; name=file; filename=import.csv");
            bodyBuilder.part("title", "CSV Import", MediaType.TEXT_PLAIN);

            webClient.post()
                    .uri("/api/v1/admin/transfer/import/file")
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                    .exchange()
                    .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    void shouldReturnBadRequestOnEmptyCsvFile() throws IOException {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        byte[] file = new ClassPathResource("transfer/import/import-empty.csv").getInputStream().readAllBytes();
        bodyBuilder.part("file", new ByteArrayResource(file)).header("Content-Disposition", "form-data; name=file; filename=import-empty.csv");
        bodyBuilder.part("title", "CSV Import", MediaType.TEXT_PLAIN);

        webClient.post()
                .uri("/api/v1/admin/transfer/import/file")
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$[0].type").isEqualTo("EMPTY");
    }

    @Test
    void shouldReturnBadRequestOnWrongFile() throws IOException {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        byte[] file = new ClassPathResource("files/dummy.png").getInputStream().readAllBytes();
        bodyBuilder.part("file", new ByteArrayResource(file)).header("Content-Disposition", "form-data; name=file; filename=dummy.png");
        bodyBuilder.part("title", "CSV Import", MediaType.TEXT_PLAIN);

        webClient.post()
                .uri("/api/v1/admin/transfer/import/file")
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @DataSet({
            "datasets/steps/transfer-steps.json",
            "datasets/specializations/transfer-specializations.json",
            "datasets/subjects/transfer-subjects.json"})
    @ExpectedDataSet({
            "datasets/expected/imports/excel-import-creation.json",
            "datasets/expected/tests/file-import-creation-tests.json",
            "datasets/expected/variants/file-import-creation-variants.json",
            "datasets/expected/tests-subjects/file-import-creation-tests-subjects.json"})
    void shouldImportTestsFromExcelFile() throws IOException {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        byte[] file = new ClassPathResource("transfer/import/import.xlsx").getInputStream().readAllBytes();
        bodyBuilder.part("file", new ByteArrayResource(file)).header("Content-Disposition", "form-data; name=file; filename=import.xlsx");
        bodyBuilder.part("title", "Excel Import", MediaType.TEXT_PLAIN);

        webClient.post()
                .uri("/api/v1/admin/transfer/import/file")
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DataSet({
            "datasets/steps/transfer-steps.json",
            "datasets/specializations/transfer-specializations.json",
            "datasets/subjects/transfer-subjects.json"})
    @ExpectedDataSet({
            "datasets/expected/imports/excel-import-creation.json",
            "datasets/expected/tests/import-creation-comment-absent-tests.json",
            "datasets/expected/variants/file-import-creation-variants.json",
            "datasets/expected/tests-subjects/file-import-creation-tests-subjects.json"})
    void shouldImportTestsFromExcelFileWithAbsentCommentColumn() throws IOException {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        byte[] file = new ClassPathResource("transfer/import/import-absent-column.xlsx").getInputStream().readAllBytes();
        bodyBuilder.part("file", new ByteArrayResource(file)).header("Content-Disposition", "form-data; name=file; filename=import.xlsx");
        bodyBuilder.part("title", "Excel Import", MediaType.TEXT_PLAIN);

        webClient.post()
                .uri("/api/v1/admin/transfer/import/file")
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnInternalServerErrorWhenInvalidFile() throws IOException {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        byte[] file = new ClassPathResource("files/dummy.png").getInputStream().readAllBytes();
        bodyBuilder.part("file", new ByteArrayResource(file)).header("Content-Disposition", "form-data; name=file; filename=dummy.png");
        bodyBuilder.part("title", "Excel Import", MediaType.TEXT_PLAIN);

        webClient.post()
                .uri("/api/v1/admin/transfer/import/file")
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @DataSet({
            "datasets/steps/transfer-steps.json",
            "datasets/specializations/transfer-specializations.json"})
    @ExpectedDataSet({
            "datasets/expected/imports/testing-ukr-import-creation.json",
            "datasets/expected/tests/testing-ukr-import-creation-tests.json",
            "datasets/expected/variants/testing-ukr-import-creation-variants.json"})
    void shouldImportTestsFromWebResource() {
        webClient.post()
                .uri("/api/v1/admin/transfer/import/web")
                .bodyValue(new WebImportDto("testing.ukr Import", "import.html"))
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
            "datasets/subjects/subjects.json",
            "datasets/tests/tests.json",
            "datasets/tests-variants/tests-variants.json",
            "datasets/tests-subjects/tests-subjects.json"})
    void shouldExportTestsToCsvFormat() {
        byte[] bytes = webClient.post()
                .uri("/api/v1/admin/transfer/export")
                .bodyValue(new ExportDto(104L, FileType.CSV))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .returnResult()
                .getResponseBody();

        byte[] bytesToCompare = ("\"#\",Питання,Коментар,Предмет,Варіант,Пояснення,+/-" + System.lineSeparator() +
                "1,Example question,Comment 1,\"Subject 1|Specialization 3 2|STEP 2,Subject 2|Specialization 1 2|STEP 2\",Just another test for trying export to CSV/EXCEL 2,Just another test variant for trying export to CSV/EXCEL 2,-" + System.lineSeparator() +
                "1,Example question,Comment 1,\"Subject 1|Specialization 3 2|STEP 2,Subject 2|Specialization 1 2|STEP 2\",Just another test for trying export to CSV/EXCEL 1,Just another test variant for trying export to CSV/EXCEL 1,+" + System.lineSeparator() +
                "2,Just for exam test,Comment 2,\"Subject 1|Specialization 3 2|STEP 2,Subject 2|Specialization 1 2|STEP 2,Subject 3|Specialization 1 1|STEP 1\",another test 1,another test variant 1,-" + System.lineSeparator() +
                "2,Just for exam test,Comment 2,\"Subject 1|Specialization 3 2|STEP 2,Subject 2|Specialization 1 2|STEP 2,Subject 3|Specialization 1 1|STEP 1\",another test 2,another test variant 2,+" + System.lineSeparator()).getBytes();

        assertNotNull(bytes);
        for (int i = 0; i < bytesToCompare.length; i++) {
            assertEquals(bytesToCompare[i], bytes[i]);
        }
    }

    @Test
    void shouldReturnInternalServerErrorWhenIOExceptionOnCsvExport() {
        try (MockedConstruction<CSVPrinter> ignored = mockConstruction(
                CSVPrinter.class,
                withSettings().defaultAnswer(invocation -> {
                    throw new IOException();
                })
        )) {
            webClient.post()
                    .uri("/api/v1/admin/transfer/export")
                    .bodyValue(new ExportDto(104L, FileType.CSV))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .exchange()
                    .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    @DataSet({
            "datasets/steps/steps.json",
            "datasets/specializations/specializations.json",
            "datasets/subjects/subjects.json",
            "datasets/tests/tests.json",
            "datasets/tests-variants/tests-variants.json",
            "datasets/tests-subjects/tests-subjects.json"})
    void shouldExportTestsToXlsxFormat() {
        byte[] bytes = webClient.post()
                .uri("/api/v1/admin/transfer/export")
                .bodyValue(new ExportDto(104L, FileType.XLSX))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .returnResult()
                .getResponseBody();

        assertNotNull(bytes);
        assertTrue(bytes.length > 0);
    }

    @Test
    void shouldReturnInternalServerErrorWhenIOExceptionOnExcelExport() {
        try (MockedStatic<WorkbookFactory> utilities = mockStatic(WorkbookFactory.class)) {
            utilities.when(() -> WorkbookFactory.create(anyBoolean())).thenThrow(new IOException());

            webClient.post()
                    .uri("/api/v1/admin/transfer/export")
                    .bodyValue(new ExportDto(104L, FileType.XLSX))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .exchange()
                    .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    @DataSet({
            "datasets/tests/tests.json",
            "datasets/imports/imports.json",
            "datasets/import-tests/import-tests.json"})
    void shouldFilterImports() {
        webClient.post()
                .uri("/api/v1/admin/transfer/import/stats/filter?pageSize=2&pageNumber=1")
                .bodyValue(new AdminImportTestFilterDto("title"))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("$.items.length()").isEqualTo(1)
                .jsonPath("$.items[0].id").isEqualTo(101)
                .jsonPath("$.items[0].activeCount").isEqualTo(2)
                .jsonPath("$.items[0].draftCount").isEqualTo(1)
                .jsonPath("$.rows").isEqualTo(3)
                .jsonPath("$.pageSize").isEqualTo(2)
                .jsonPath("$.pagesCount").isEqualTo(2);
    }
}
