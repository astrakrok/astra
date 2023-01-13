package com.example.astraapi.factory;

import com.example.astraapi.meta.DocumentType;
import com.example.astraapi.service.FileExporter;

public interface ExporterFactory {
    FileExporter getFileExporter(DocumentType documentType);
}
