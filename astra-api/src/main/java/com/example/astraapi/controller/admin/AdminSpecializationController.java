package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminSpecializationFilterDto;
import com.example.astraapi.dto.specialization.SpecializationDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Endpoint.ADMIN_SPECIALIZATIONS)
@RequiredArgsConstructor
public class AdminSpecializationController {
    private final SpecializationService specializationService;

    @PostMapping
    public ResponseEntity<IdDto> save(@Valid @RequestBody SpecializationDto specializationDto) {
        IdDto idDto = specializationService.save(specializationDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(idDto);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<SpecializationDto>> search(
            @RequestBody AdminSpecializationFilterDto filter
    ) {
        List<SpecializationDto> items = specializationService.search(filter);
        return ResponseEntity.ok(items);
    }
}
