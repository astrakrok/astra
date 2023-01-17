package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.dto.filter.AdminImportTestFilterDto;
import com.example.astraapi.dto.importing.FileImportDto;
import com.example.astraapi.dto.importing.ImportStatsDto;
import com.example.astraapi.dto.importing.WebImportDto;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;

public interface TransferService {
    IdDto importFromFile(FileImportDto fileImportDto);

    IdDto importFromWeb(WebImportDto webImportDto);

    Page<ImportStatsDto> search(AdminImportTestFilterDto filter, Pageable pageable);

    byte[] exportTests(ExportDto exportDto);
}
