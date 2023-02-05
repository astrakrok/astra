package com.example.astraapi.service;

import com.example.astraapi.TestUtils;
import com.example.astraapi.exception.ImportException;
import com.example.astraapi.meta.ImportSource;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.importing.ImportResult;
import com.example.astraapi.model.importing.ImportTest;
import com.example.astraapi.service.impl.ExcelFileImporter;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class ExcelFileImportTest {
    @InjectMocks
    private ExcelFileImporter fileImporter;

    @Test
    void shouldImportTests() throws IOException {
        MockMultipartFile file = new MockMultipartFile("data", "import.xlsx", "text/plain", TestUtils.getResourceStream("transfer/import/import.xlsx"));

        ImportResult importResult = fileImporter.importTests(file);

        assertEquals(1, importResult.getTests().size());
        assertEquals(ImportSource.EXCEL_FILE, importResult.getSource());
        assertEquals("import.xlsx", importResult.getSourceTitle());
        ImportTest test = importResult.getTests().get(0);
        assertEquals("question text", test.getQuestion());
        assertEquals("comment text", test.getComment());

        assertEquals(3, test.getSubjects().size());

        assertEquals("subject 1", test.getSubjects().get(0).getSubjectTitle());
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
    void shouldThrowImportException() throws IOException {
        try (MockedStatic<WorkbookFactory> utilities = mockStatic(WorkbookFactory.class)) {
            utilities.when(() -> WorkbookFactory.create((InputStream) any())).thenThrow(IOException.class);

            MockMultipartFile file = new MockMultipartFile("data", "import.xlsx", "text/plain", TestUtils.getResourceStream("transfer/import/import.xlsx"));
            ImportException exception = assertThrows(ImportException.class, () -> fileImporter.importTests(file));
            assertEquals(ValidationErrorType.UNKNOWN, exception.getType());
        }
    }

    @Test
    void shouldImportSingleLineTest() throws IOException {
        MockMultipartFile file = new MockMultipartFile("data", "import-single-line-test.xlsx", "text/plain", TestUtils.getResourceStream("transfer/import/import-single-line-test.xlsx"));

        ImportResult importResult = fileImporter.importTests(file);

        assertEquals(1, importResult.getTests().size());
        assertEquals(ImportSource.EXCEL_FILE, importResult.getSource());
        assertEquals("import-single-line-test.xlsx", importResult.getSourceTitle());
        ImportTest test = importResult.getTests().get(0);
        assertEquals("question text", test.getQuestion());
        assertEquals("comment text", test.getComment());

        assertEquals(3, test.getSubjects().size());

        assertEquals("subject 1", test.getSubjects().get(0).getSubjectTitle());
        assertNull(test.getSubjects().get(0).getStepTitle());

        assertEquals("subject 2", test.getSubjects().get(1).getSubjectTitle());
        assertEquals("specialization 2", test.getSubjects().get(1).getSpecializationTitle());
        assertEquals("step 2", test.getSubjects().get(1).getStepTitle());

        assertEquals("subject 3", test.getSubjects().get(2).getSubjectTitle());
        assertNull(test.getSubjects().get(2).getSpecializationTitle());
        assertNull(test.getSubjects().get(2).getStepTitle());

        assertEquals(1, test.getVariants().size());

        assertEquals("variant title 1", test.getVariants().get(0).getTitle());
        assertEquals("variant explanation 1", test.getVariants().get(0).getExplanation());
    }

    @Test
    void shouldImportTestWithAbsentCommentColumn() throws IOException {
        MockMultipartFile file = new MockMultipartFile("data", "import-absent-column.xlsx", "text/plain", TestUtils.getResourceStream("transfer/import/import-absent-column.xlsx"));

        ImportResult importResult = fileImporter.importTests(file);

        assertEquals(1, importResult.getTests().size());
        assertEquals(ImportSource.EXCEL_FILE, importResult.getSource());
        assertEquals("import-absent-column.xlsx", importResult.getSourceTitle());
        ImportTest test = importResult.getTests().get(0);
        assertEquals("question text", test.getQuestion());

        assertEquals(3, test.getSubjects().size());

        assertEquals("subject 1", test.getSubjects().get(0).getSubjectTitle());
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
}
