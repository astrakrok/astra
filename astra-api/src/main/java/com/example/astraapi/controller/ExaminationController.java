package com.example.astraapi.controller;

import com.example.astraapi.dto.examination.ExaminationAnswerDto;
import com.example.astraapi.dto.examination.ExaminationResultDto;
import com.example.astraapi.dto.examination.ExaminationSearchDto;
import com.example.astraapi.dto.examination.ExaminationStateDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.EXAMINATIONS)
@RequiredArgsConstructor
public class ExaminationController {
    private final ExaminationService examinationService;

    @PostMapping
    public ResponseEntity<ExaminationStateDto> start(@Valid @RequestBody ExaminationSearchDto searchDto) {
        ExaminationStateDto examination = examinationService.start(searchDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(examination);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAnswer(
            @PathVariable("id") Long id,
            @Valid @RequestBody ExaminationAnswerDto examinationAnswerDto
    ) {
        examinationService.updateAnswer(id, examinationAnswerDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/result")
    public ResponseEntity<ExaminationResultDto> finishTest(@PathVariable("id") Long id) {
        ExaminationResultDto examinationResult = examinationService.finish(id);
        return ResponseEntity.ok().body(examinationResult);
    }
}
