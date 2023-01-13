package com.example.astraapi.service;

import com.example.astraapi.dto.exporting.ExportDto;

public interface ExportService {
    byte[] exportTests(ExportDto exportDto);
}
