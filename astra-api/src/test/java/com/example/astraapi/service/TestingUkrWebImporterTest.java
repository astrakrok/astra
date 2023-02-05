package com.example.astraapi.service;

import com.example.astraapi.TestUtils;
import com.example.astraapi.meta.ImportSource;
import com.example.astraapi.model.importing.ImportResult;
import com.example.astraapi.model.importing.ImportTest;
import com.example.astraapi.service.impl.TestingUkrWebImporterImpl;
import com.example.astraapi.util.JsoupUtils;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class TestingUkrWebImporterTest {
    @InjectMocks
    private TestingUkrWebImporterImpl webImporter;

    @Test
    void shouldImportTests() {
        try (MockedStatic<JsoupUtils> utilities = mockStatic(JsoupUtils.class)) {
            utilities.when(() -> JsoupUtils.getDocument(eq("transfer/import/import.html"))).thenAnswer(invocation -> Jsoup.parse(TestUtils.getResourceFile("transfer/import/import.html")));

            ImportResult importResult = webImporter.importTests("transfer/import/import.html");

            assertEquals(ImportSource.TESTING_UKR_WEB, importResult.getSource());
            assertEquals("Testing title", importResult.getSourceTitle());
            assertEquals("transfer/import/import.html", importResult.getDetails().get("url"));
            assertEquals(2, importResult.getTests().size());

            ImportTest test = importResult.getTests().get(0);

            assertEquals("question text", test.getQuestion());
            assertEquals(5, test.getVariants().size());

            assertEquals("variant title 1", test.getVariants().get(0).getTitle());
            assertTrue(test.getVariants().get(0).isCorrect());

            assertEquals("variant title 2", test.getVariants().get(1).getTitle());
            assertFalse(test.getVariants().get(1).isCorrect());

            assertEquals("variant title 3", test.getVariants().get(2).getTitle());
            assertFalse(test.getVariants().get(2).isCorrect());

            assertEquals("variant title 4", test.getVariants().get(3).getTitle());
            assertFalse(test.getVariants().get(3).isCorrect());

            assertEquals("variant title 5", test.getVariants().get(4).getTitle());
            assertFalse(test.getVariants().get(4).isCorrect());

            test = importResult.getTests().get(1);
            assertEquals("question text another", test.getQuestion());
            assertEquals(5, test.getVariants().size());

            assertEquals("variant title another 1", test.getVariants().get(0).getTitle());
            assertFalse(test.getVariants().get(0).isCorrect());

            assertEquals("variant title another 2", test.getVariants().get(1).getTitle());
            assertFalse(test.getVariants().get(1).isCorrect());

            assertEquals("variant title another 3", test.getVariants().get(2).getTitle());
            assertTrue(test.getVariants().get(2).isCorrect());

            assertEquals("variant title another 4", test.getVariants().get(3).getTitle());
            assertFalse(test.getVariants().get(3).isCorrect());

            assertEquals("variant title another 5", test.getVariants().get(4).getTitle());
            assertFalse(test.getVariants().get(4).isCorrect());
        }
    }
}
