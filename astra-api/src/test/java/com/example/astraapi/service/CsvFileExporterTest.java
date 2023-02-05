package com.example.astraapi.service;

import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.factory.CsvFactory;
import com.example.astraapi.meta.FileType;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.exporting.ExportSubject;
import com.example.astraapi.model.exporting.ExportTest;
import com.example.astraapi.model.exporting.ExportVariant;
import com.example.astraapi.service.impl.CsvFileExporter;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvFileExporterTest {
    @InjectMocks
    private CsvFileExporter fileExporter;
    @Mock
    private TestService testService;
    @Mock
    private CsvFactory csvFactory;

    @Test
    void shouldExportTests() throws IOException {
        Page<ExportTest> exportPage = new Page<>();
        exportPage.setPageSize(1);
        exportPage.setRows(1);
        exportPage.setItems(List.of(new ExportTest(
                "question",
                "comment",
                List.of(new ExportVariant("title", "explanation", true)),
                List.of(new ExportSubject("title", "specializationTitle", "stepTitle"))
        )));
        when(testService.getTestsForExport(any(), any())).thenReturn(exportPage);
        when(csvFactory.newPrinter(any(), any())).thenAnswer(invocation -> new CSVPrinter(invocation.getArgument(0), invocation.getArgument(1)));

        byte[] bytes = fileExporter.exportTests(new ExportDto(5L, FileType.CSV));
        byte[] bytesToCompare = ("\"#\",Питання,Коментар,Предмет,Варіант,Пояснення,+/-" + System.lineSeparator() +
                "1,question,comment,title|specializationTitle|stepTitle,title,explanation,+" + System.lineSeparator()).getBytes();
        for (int i = 0; i < bytesToCompare.length; i++) {
            assertEquals(bytesToCompare[i], bytes[i]);
        }
    }

    @Test
    void shouldWrapIOException() throws IOException {
        when(csvFactory.newPrinter(any(), any())).thenThrow(new IOException());

        assertThrows(RuntimeException.class, () -> fileExporter.exportTests(new ExportDto(5L, FileType.CSV)));
    }
}
