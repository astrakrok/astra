package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.specialization.RequestSpecializationDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.ADMIN_STEP_SPECIALIZATIONS)
@RequiredArgsConstructor
public class AdminStepSpecializationController {
    private final SpecializationService specializationService;

    @PostMapping
    public ResponseEntity<IdDto> save(
            @PathVariable("stepId") Long stepId,
            @Valid @RequestBody RequestSpecializationDto specializationDto
    ) {
        IdDto idDto = specializationService.save(stepId, specializationDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(idDto);
    }
}
