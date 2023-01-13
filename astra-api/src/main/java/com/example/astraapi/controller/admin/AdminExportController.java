package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.ADMIN_EXPORT)
@RequiredArgsConstructor
public class AdminExportController {
    private final ExportService exportService;

    @GetMapping
    public byte[] exportTests(@Valid ExportDto exportDto) {
        return exportService.exportTests(exportDto);
    }
}
