package com.example.astraapi.service.impl;

import com.example.astraapi.service.FileImporter;
import com.example.astraapi.service.FileImporterFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FileImporterFactoryImpl implements FileImporterFactory {
    private final FileImporter excelFileImporter;

    public FileImporterFactoryImpl(
            @Qualifier("excelFileImporter") FileImporter excelFileImporter
    ) {
        this.excelFileImporter = excelFileImporter;
    }

    @Override
    public FileImporter get(String fileName) {
        return excelFileImporter;
    }
}
