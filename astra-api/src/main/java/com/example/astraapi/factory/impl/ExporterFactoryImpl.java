package com.example.astraapi.factory.impl;

import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.factory.ExporterFactory;
import com.example.astraapi.meta.DocumentType;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.service.FileExporter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExporterFactoryImpl implements ExporterFactory {
    private final FileExporter excelFileExporter;

    public ExporterFactoryImpl(
            @Qualifier("excelFileExporter") FileExporter excelFileExporter
    ) {
        this.excelFileExporter = excelFileExporter;
    }

    @Override
    public FileExporter getFileExporter(DocumentType documentType) {
        switch (documentType) {
            case EXCEL:
                return excelFileExporter;
            default:
                throw new ValidationException(new ValidationError(ValidationErrorType.UNSUPPORTED_DOCUMENT_TYPE));
        }
    }
}
