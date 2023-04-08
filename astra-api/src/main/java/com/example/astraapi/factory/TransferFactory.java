package com.example.astraapi.factory;

import com.example.astraapi.meta.FileType;
import com.example.astraapi.service.FileExporter;
import com.example.astraapi.service.FileImporter;
import com.example.astraapi.service.WebImporter;

public interface TransferFactory {
    FileImporter getFileImporter(String fileName);

    WebImporter getWebImporter(String url);

    FileExporter getFileExporter(FileType fileType);
}
