package com.example.astraapi.service.impl;

import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.factory.TransferFactory;
import com.example.astraapi.service.ExportService;
import com.example.astraapi.service.FileExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {
    private final TransferFactory transferFactory;

    @Override
    @Transactional
    public byte[] exportTests(ExportDto exportDto) {
        FileExporter fileExporter = transferFactory.getFileExporter(exportDto.getFileType());
        return fileExporter.exportTests(exportDto);
    }
}
