package com.example.astraapi.service;

import com.example.astraapi.TestUtils;
import com.example.astraapi.exception.ImportException;
import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.factory.CsvFactory;
import com.example.astraapi.meta.ImportSource;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.importing.ImportResult;
import com.example.astraapi.model.importing.ImportTest;
import com.example.astraapi.service.impl.CsvFileImporter;
import com.example.astraapi.util.FileUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvFileImporterTest {
    @InjectMocks
    private CsvFileImporter fileImporter;
    @Mock
    private CsvFactory csvFactory;

    @Test
    void shouldImportTests() throws IOException {
        when(csvFactory.newParser(any(), any())).thenAnswer(invocation -> {
            MultipartFile file = invocation.getArgument(0);
            CSVFormat format = invocation.getArgument(1);

            return new CSVParser(new InputStreamReader(FileUtils.getInputStream(file), StandardCharsets.UTF_8), format);
        });

        MockMultipartFile file = new MockMultipartFile("data", "import.csv", "text/plain", TestUtils.getResourceStream("transfer/import/import.csv"));
        ImportResult importResult = fileImporter.importTests(file);

        assertEquals(1, importResult.getTests().size());
        assertEquals(ImportSource.CSV_FILE, importResult.getSource());
        assertEquals("import.csv", importResult.getSourceTitle());
        ImportTest test = importResult.getTests().get(0);
        assertEquals("question text", test.getQuestion());
        assertEquals("comment text", test.getComment());

        assertEquals(3, test.getSubjects().size());

        assertEquals("subject 1", test.getSubjects().get(0).getSubjectTitle());
        assertEquals("specialization 1", test.getSubjects().get(0).getSpecializationTitle());
        assertNull(test.getSubjects().get(0).getStepTitle());

        assertEquals("subject 2", test.getSubjects().get(1).getSubjectTitle());
        assertEquals("specialization 2", test.getSubjects().get(1).getSpecializationTitle());
        assertEquals("step 2", test.getSubjects().get(1).getStepTitle());

        assertEquals("subject 3", test.getSubjects().get(2).getSubjectTitle());
        assertNull(test.getSubjects().get(2).getSpecializationTitle());
        assertNull(test.getSubjects().get(2).getStepTitle());

        assertEquals(5, test.getVariants().size());

        assertEquals("variant title 1", test.getVariants().get(0).getTitle());
        assertEquals("variant explanation 1", test.getVariants().get(0).getExplanation());

        assertEquals("variant title 2", test.getVariants().get(1).getTitle());
        assertEquals("variant explanation 2", test.getVariants().get(1).getExplanation());

        assertEquals("variant title 3", test.getVariants().get(2).getTitle());
        assertEquals("variant explanation 3", test.getVariants().get(2).getExplanation());

        assertEquals("variant title 4", test.getVariants().get(3).getTitle());
        assertEquals("variant explanation 4", test.getVariants().get(3).getExplanation());

        assertEquals("variant title 5", test.getVariants().get(4).getTitle());
        assertEquals("variant explanation 5", test.getVariants().get(4).getExplanation());
    }

    @Test
    void shouldThrowExceptionOnEmptyFile() throws IOException {
        when(csvFactory.newParser(any(), any())).thenAnswer(invocation -> {
            MultipartFile file = invocation.getArgument(0);
            CSVFormat format = invocation.getArgument(1);

            return new CSVParser(new InputStreamReader(FileUtils.getInputStream(file), StandardCharsets.UTF_8), format);
        });
        
        MockMultipartFile file = new MockMultipartFile("data", "import-empty.csv", "text/plain", TestUtils.getResourceStream("transfer/import/import-empty.csv"));

        ValidationException exception = assertThrows(ValidationException.class, () -> fileImporter.importTests(file));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.EMPTY, exception.getErrors().get(0).getType());
    }

    @Test
    void shouldThrowImportException() throws IOException {
        when(csvFactory.newParser(any(), any())).thenThrow(new IOException());

        MockMultipartFile file = new MockMultipartFile("data", "import-empty.csv", "text/plain", TestUtils.getResourceStream("transfer/import/import-empty.csv"));
        ImportException exception = assertThrows(ImportException.class, () -> fileImporter.importTests(file));
        assertEquals(ValidationErrorType.UNKNOWN, exception.getType());
    }
}
