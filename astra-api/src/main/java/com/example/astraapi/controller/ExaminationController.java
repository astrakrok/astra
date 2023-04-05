package com.example.astraapi.controller;

import com.example.astraapi.dto.examination.ExaminationAnswerDto;
import com.example.astraapi.dto.examination.ExaminationResultDto;
import com.example.astraapi.dto.examination.ExaminationSearchDto;
import com.example.astraapi.dto.examination.ExaminationStateDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.EXAMINATIONS)
@RequiredArgsConstructor
public class ExaminationController {
    private final ExaminationService examinationService;

    @PostMapping
    public ExaminationStateDto start(@Valid @RequestBody ExaminationSearchDto searchDto) {
        return examinationService.start(searchDto);
    }

    @PutMapping("/{id}")
    public void updateAnswer(
            @PathVariable("id") Long id,
            @Valid @RequestBody ExaminationAnswerDto examinationAnswerDto
    ) {
        examinationService.updateAnswer(id, examinationAnswerDto);
    }

    @PutMapping("/{id}/result")
    public ExaminationResultDto finishTest(@PathVariable("id") Long id) {
        return examinationService.finish(id);
    }
}
