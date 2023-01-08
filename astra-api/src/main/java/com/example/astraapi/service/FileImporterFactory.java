package com.example.astraapi.service;

public interface FileImporterFactory {
  FileImporter get(String fileName);
}
