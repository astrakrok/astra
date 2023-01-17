package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.dto.filter.AdminImportTestFilterDto;
import com.example.astraapi.dto.importing.FileImportDto;
import com.example.astraapi.dto.importing.ImportStatsDto;
import com.example.astraapi.dto.importing.WebImportDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.ADMIN_TRANSFER)
@RequiredArgsConstructor
public class AdminTransferController {
    private final TransferService transferService;

    @PostMapping(value = "/import/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public IdDto importFromFile(@Valid @ModelAttribute FileImportDto fileImportDto) {
        return transferService.importFromFile(fileImportDto);
    }

    @PostMapping("/import/web")
    public IdDto importFromWeb(@Valid @RequestBody WebImportDto webImportDto) {
        return transferService.importFromWeb(webImportDto);
    }

    @PostMapping(value = "/import/stats/filter")
    public Page<ImportStatsDto> search(
            @RequestBody AdminImportTestFilterDto filter,
            Pageable pageable
    ) {
        return transferService.search(filter, pageable);
    }

    @PostMapping("/export")
    public byte[] exportTests(@Valid @RequestBody ExportDto exportDto) {
        return transferService.exportTests(exportDto);
    }
}
