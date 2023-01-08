package com.example.astraapi.service;

import com.example.astraapi.dto.FileImportDto;
import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminImportTestFilterDto;
import com.example.astraapi.dto.importing.ImportStatsDto;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;

public interface ImportService {
    IdDto importFromFile(FileImportDto fileImportDto);

    Page<ImportStatsDto> search(AdminImportTestFilterDto filter, Pageable pageable);
}
