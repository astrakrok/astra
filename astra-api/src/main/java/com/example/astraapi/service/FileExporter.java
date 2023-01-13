package com.example.astraapi.service;

import com.example.astraapi.dto.exporting.ExportDto;

public interface FileExporter {
    byte[] exportTests(ExportDto exportDto);
}
