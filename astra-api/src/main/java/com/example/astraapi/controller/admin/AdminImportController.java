package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminImportTestFilterDto;
import com.example.astraapi.dto.importing.FileImportDto;
import com.example.astraapi.dto.importing.ImportStatsDto;
import com.example.astraapi.dto.importing.WebImportDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.service.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.ADMIN_IMPORT)
@RequiredArgsConstructor
public class AdminImportController {
    private final ImportService importService;

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public IdDto importFromFile(@Valid @ModelAttribute FileImportDto fileImportDto) {
        return importService.importFromFile(fileImportDto);
    }

    @PostMapping("/web")
    public IdDto importFromWeb(@Valid @RequestBody WebImportDto webImportDto) {
        return importService.importFromWeb(webImportDto);
    }

    @PostMapping(value = "/stats/filter")
    public Page<ImportStatsDto> search(
            @RequestBody AdminImportTestFilterDto filter,
            Pageable pageable
    ) {
        return importService.search(filter, pageable);
    }
}
