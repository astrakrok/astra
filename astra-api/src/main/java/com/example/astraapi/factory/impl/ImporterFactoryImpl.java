package com.example.astraapi.factory.impl;

import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.factory.ImporterFactory;
import com.example.astraapi.meta.LinkSource;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.service.FileImporter;
import com.example.astraapi.service.LinkService;
import com.example.astraapi.service.WebImporter;
import com.example.astraapi.service.impl.TestingUkrWebImporterImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImporterFactoryImpl implements ImporterFactory {
    private final LinkService linkService;
    private final FileImporter excelFileImporter;
    private final WebImporter testingUkrWebImporter;

    public ImporterFactoryImpl(
            LinkService linkService,
            @Qualifier("excelFileImporter") FileImporter excelFileImporter,
            @Qualifier("testingUkrWebImporter") TestingUkrWebImporterImpl testingUkrWebImporter
    ) {
        this.linkService = linkService;
        this.excelFileImporter = excelFileImporter;
        this.testingUkrWebImporter = testingUkrWebImporter;
    }

    @Override
    public FileImporter getFileImporter(String fileName) {
        return excelFileImporter;
    }

    @Override
    public WebImporter getWebImporter(String url) {
        LinkSource source = linkService.getSource(url);
        if (source == LinkSource.TESTING_UKR) {
            return testingUkrWebImporter;
        }
        throw new ValidationException(new ValidationError(ValidationErrorType.UNKNOWN_SOURCE));
    }
}
