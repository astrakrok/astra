package com.example.astraapi.factory.impl;

import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.factory.TransferFactory;
import com.example.astraapi.meta.FileType;
import com.example.astraapi.meta.LinkSource;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.service.FileExporter;
import com.example.astraapi.service.FileImporter;
import com.example.astraapi.service.LinkService;
import com.example.astraapi.service.WebImporter;
import com.example.astraapi.service.impl.TestingUkrWebImporterImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TransferFactoryImpl implements TransferFactory {
    private final LinkService linkService;
    private final FileImporter excelFileImporter;
    private final FileImporter csvFileImporter;
    private final WebImporter testingUkrWebImporter;
    private final FileExporter excelFileExporter;
    private final FileExporter csvFileExporter;

    public TransferFactoryImpl(
            LinkService linkService,
            @Qualifier("excelFileImporter") FileImporter excelFileImporter,
            @Qualifier("csvFileImporter") FileImporter csvFileImporter,
            @Qualifier("testingUkrWebImporter") TestingUkrWebImporterImpl testingUkrWebImporter,
            @Qualifier("excelFileExporter") FileExporter excelFileExporter,
            @Qualifier("csvFileExporter") FileExporter csvFileExporter
    ) {
        this.linkService = linkService;
        this.excelFileImporter = excelFileImporter;
        this.csvFileImporter = csvFileImporter;
        this.testingUkrWebImporter = testingUkrWebImporter;
        this.excelFileExporter = excelFileExporter;
        this.csvFileExporter = csvFileExporter;
    }

    @Override
    public FileImporter getFileImporter(String fileName) {
        int lastDotPosition = fileName.lastIndexOf(".");
        String extension = fileName.substring(lastDotPosition + 1);
        if (StringUtils.equalsAnyIgnoreCase(extension, "xlsx", "xls")) {
            return excelFileImporter;
        } else if (StringUtils.equalsAnyIgnoreCase(extension, "csv")) {
            return csvFileImporter;
        }
        throw new ValidationException(new ValidationError(ValidationErrorType.UNKNOWN_SOURCE));
    }

    @Override
    public WebImporter getWebImporter(String url) {
        LinkSource source = linkService.getSource(url);
        if (source == LinkSource.TESTING_UKR) {
            return testingUkrWebImporter;
        }
        throw new ValidationException(new ValidationError(ValidationErrorType.UNKNOWN_SOURCE));
    }

    @Override
    public FileExporter getFileExporter(FileType fileType) {
        switch (fileType) {
            case XLS:
            case XLSX:
                return excelFileExporter;
            case CSV:
                return csvFileExporter;
            default:
                throw new ValidationException(new ValidationError(ValidationErrorType.UNSUPPORTED_DOCUMENT_TYPE));
        }
    }
}
