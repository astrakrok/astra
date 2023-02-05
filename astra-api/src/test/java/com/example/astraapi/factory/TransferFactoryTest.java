package com.example.astraapi.factory;

import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.factory.impl.TransferFactoryImpl;
import com.example.astraapi.meta.FileType;
import com.example.astraapi.meta.LinkSource;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.service.FileExporter;
import com.example.astraapi.service.FileImporter;
import com.example.astraapi.service.LinkService;
import com.example.astraapi.service.WebImporter;
import com.example.astraapi.service.impl.LinkServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TransferFactoryTest {
    private TransferFactoryImpl transferFactory;

    private FileImporter excelFileImporter;
    private FileImporter csvFileImporter;
    private WebImporter testingUkrWebImporter;
    private FileExporter excelFileExporter;
    private FileExporter csvFileExporter;

    @BeforeEach
    public void beforeEach() {
        LinkService linkService = mock(LinkServiceImpl.class);
        excelFileImporter = mock(FileImporter.class);
        csvFileImporter = mock(FileImporter.class);
        testingUkrWebImporter = mock(WebImporter.class);
        excelFileExporter = mock(FileExporter.class);
        csvFileExporter = mock(FileExporter.class);

        lenient().when(linkService.getSource(eq("https://тестування.укр/example-link.html"))).thenReturn(LinkSource.TESTING_UKR);
        lenient().when(linkService.getSource(eq("https://xn--80adi8aaufcj8j.xn--j1amh/example-link.html"))).thenReturn(LinkSource.TESTING_UKR);
        lenient().when(linkService.getSource(eq("unknown-source"))).thenReturn(LinkSource.UNKNOWN);

        transferFactory = new TransferFactoryImpl(linkService, excelFileImporter, csvFileImporter, testingUkrWebImporter, excelFileExporter, csvFileExporter);
    }

    @Test
    public void shouldReturnExcelFileImporterWhenXlsExtension() {
        String fileName = "test-file.xls";
        FileImporter fileImporter = transferFactory.getFileImporter(fileName);

        assertEquals(excelFileImporter, fileImporter);
    }

    @Test
    public void shouldReturnExcelFileImporterWhenXlsxExtension() {
        String fileName = "test-file.xlsx";
        FileImporter fileImporter = transferFactory.getFileImporter(fileName);

        assertEquals(excelFileImporter, fileImporter);
    }

    @Test
    public void shouldReturnCsvFileImporter() {
        String fileName = "test-file.csv";
        FileImporter fileImporter = transferFactory.getFileImporter(fileName);

        assertEquals(csvFileImporter, fileImporter);
    }

    @Test
    public void shouldReturnTestingUkrWebImporterWhenCyrillicLink() {
        String link = "https://тестування.укр/example-link.html";
        WebImporter webImporter = transferFactory.getWebImporter(link);

        assertEquals(testingUkrWebImporter, webImporter);
    }

    @Test
    public void shouldReturnTestingUkrWebImporterWhenNonCyrillicLink() {
        String link = "https://xn--80adi8aaufcj8j.xn--j1amh/example-link.html";
        WebImporter webImporter = transferFactory.getWebImporter(link);

        assertEquals(testingUkrWebImporter, webImporter);
    }

    @Test
    public void shouldReturnExcelFileExporterWhenXlsFileType() {
        FileExporter fileExporter = transferFactory.getFileExporter(FileType.XLS);

        assertEquals(excelFileExporter, fileExporter);
    }

    @Test
    public void shouldReturnExcelFileExporterWhenXlsxFileType() {
        FileExporter fileExporter = transferFactory.getFileExporter(FileType.XLSX);

        assertEquals(excelFileExporter, fileExporter);
    }

    @Test
    public void shouldReturnCsvFileExporter() {
        FileExporter fileExporter = transferFactory.getFileExporter(FileType.CSV);

        assertEquals(csvFileExporter, fileExporter);
    }

    @Test
    public void shouldThrowExceptionWhenGetFileImporterWithUnknownFileSource() {
        String fileName = "file.txt";

        ValidationException exception = assertThrows(ValidationException.class, () -> transferFactory.getFileImporter(fileName));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.UNKNOWN_SOURCE, exception.getErrors().get(0).getType());
    }

    @Test
    public void shouldThrowExceptionWhenGetWebImporterWithUnknownWebSource() {
        String link = "unknown-source";

        ValidationException exception = assertThrows(ValidationException.class, () -> transferFactory.getWebImporter(link));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.UNKNOWN_SOURCE, exception.getErrors().get(0).getType());
    }

    @Test
    public void shouldThrowExceptionWhenGetFileExporterWithNullFileType() {
        ValidationException exception = assertThrows(ValidationException.class, () -> transferFactory.getFileExporter(null));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.UNSUPPORTED_DOCUMENT_TYPE, exception.getErrors().get(0).getType());
    }
}
