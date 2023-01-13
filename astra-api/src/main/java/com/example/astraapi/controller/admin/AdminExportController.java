package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.ADMIN_EXPORT)
@RequiredArgsConstructor
public class AdminExportController {
    private final ExportService exportService;

    @PostMapping
    public byte[] exportTests(@Valid @RequestBody ExportDto exportDto) {
        return exportService.exportTests(exportDto);
    }
}
