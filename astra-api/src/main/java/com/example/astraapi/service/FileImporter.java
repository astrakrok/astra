package com.example.astraapi.service;

import com.example.astraapi.model.importing.ImportResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileImporter {
    ImportResult importTests(MultipartFile file);
}
