package com.example.astraapi.mock;

import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.factory.TransferFactory;
import com.example.astraapi.meta.ExecutionProfile;
import com.example.astraapi.meta.FileType;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.service.FileExporter;
import com.example.astraapi.service.FileImporter;
import com.example.astraapi.service.WebImporter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(ExecutionProfile.INTEGRATION_TEST)
public class TransferFactoryMock implements TransferFactory {
    private final FileImporter excelFileImporter;
    private final FileImporter csvFileImporter;
    private final WebImporter testingUkrWebImporter;
    private final FileExporter excelFileExporter;
    private final FileExporter csvFileExporter;

    public TransferFactoryMock(
            @Qualifier("excelFileImporter") FileImporter excelFileImporter,
            @Qualifier("csvFileImporter") FileImporter csvFileImporter,
            @Qualifier("testingUkrWebImporter") WebImporter testingUkrWebImporter,
            @Qualifier("excelFileExporter") FileExporter excelFileExporter,
            @Qualifier("csvFileExporter") FileExporter csvFileExporter
    ) {
        this.excelFileImporter = excelFileImporter;
        this.csvFileImporter = csvFileImporter;
        this.testingUkrWebImporter = testingUkrWebImporter;
        this.excelFileExporter = excelFileExporter;
        this.csvFileExporter = csvFileExporter;
    }

    @Override
    public FileImporter getFileImporter(String fileName) {
        if (StringUtils.equalsAnyIgnoreCase(fileName, "import.xlsx", "dummy.png")) {
            return excelFileImporter;
        } else if (StringUtils.equalsAnyIgnoreCase(fileName, "import.csv", "import-empty.csv", "dummy.png")) {
            return csvFileImporter;
        }
        throw new ValidationException(new ValidationError(ValidationErrorType.UNKNOWN_SOURCE));
    }

    @Override
    public WebImporter getWebImporter(String url) {
        if (StringUtils.equalsAnyIgnoreCase(url, "import.html")) {
            return testingUkrWebImporter;
        }
        throw new ValidationException(new ValidationError(ValidationErrorType.UNKNOWN_SOURCE));
    }

    @Override
    public FileExporter getFileExporter(FileType fileType) {
        if (fileType == null) {
            throw new ValidationException(new ValidationError(ValidationErrorType.UNSUPPORTED_DOCUMENT_TYPE));
        }
        switch (fileType) {
            case CSV:
                return csvFileExporter;
            case XLS:
            case XLSX:
                return excelFileExporter;
            default:
                throw new ValidationException(new ValidationError(ValidationErrorType.UNSUPPORTED_DOCUMENT_TYPE));
        }
    }
}
