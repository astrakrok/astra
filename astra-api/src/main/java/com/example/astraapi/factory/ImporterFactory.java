package com.example.astraapi.factory;

import com.example.astraapi.service.FileImporter;
import com.example.astraapi.service.WebImporter;

public interface ImporterFactory {
  FileImporter getFileImporter(String fileName);

  WebImporter getWebImporter(String url);
}
