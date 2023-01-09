package com.example.astraapi.service;

import com.example.astraapi.model.importing.ImportResult;

public interface WebImporter {
    ImportResult importTests(String link);
}
