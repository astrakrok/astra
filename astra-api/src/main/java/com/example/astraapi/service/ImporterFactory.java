package com.example.astraapi.service;

public interface ImporterFactory {
  FileImporter getFileImporter(String fileName);

  WebImporter getWebImporter(String url);
}
