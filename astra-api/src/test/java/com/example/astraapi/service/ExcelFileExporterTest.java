package com.example.astraapi.service;

import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.meta.FileType;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.exporting.ExportSubject;
import com.example.astraapi.model.exporting.ExportTest;
import com.example.astraapi.model.exporting.ExportVariant;
import com.example.astraapi.service.impl.ExcelFileExporter;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExcelFileExporterTest {
    @InjectMocks
    private ExcelFileExporter fileExporter;
    @Mock
    private TestService testService;

    @Test
    void shouldExportTest() {
        Page<ExportTest> exportPage = new Page<>();
        exportPage.setPageSize(1);
        exportPage.setRows(1);
        exportPage.setItems(List.of(new ExportTest(
                "question",
                "comment",
                List.of(
                        new ExportVariant("title 1", "explanation 1", true),
                        new ExportVariant("title 2", "explanation 2", false)),
                List.of(new ExportSubject("title", "specializationTitle", "stepTitle"))
        )));
        when(testService.getTestsForExport(any(), any())).thenReturn(exportPage);

        byte[] bytes = fileExporter.exportTests(new ExportDto(5L, FileType.XLSX));
        assertTrue(bytes.length > 0);
    }

    @Test
    void shouldWrapIOException() {
        try (MockedStatic<WorkbookFactory> utilities = mockStatic(WorkbookFactory.class)) {
            utilities.when(() -> WorkbookFactory.create(anyBoolean())).thenThrow(new IOException());

            assertThrows(RuntimeException.class, () -> fileExporter.exportTests(new ExportDto(5L, FileType.XLS)));
        }
    }
}
