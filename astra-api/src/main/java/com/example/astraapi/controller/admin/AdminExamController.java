package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.exam.RequestExamDto;
import com.example.astraapi.dto.specialization.StepSpecializationDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Endpoint.ADMIN_EXAMS)
@RequiredArgsConstructor
public class AdminExamController {
    private final ExamService examService;

    @PostMapping
    public ResponseEntity<IdDto> save(@Valid @RequestBody RequestExamDto examDto) {
        IdDto idDto = examService.save(examDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(idDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        examService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody RequestExamDto examDto
    ) {
        examService.update(id, examDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/specializations/available")
    public ResponseEntity<List<StepSpecializationDto>> getAvailableSpecializations(@PathVariable("id") Long id) {
        List<StepSpecializationDto> items = examService.getAvailableSpecializations(id);
        return ResponseEntity.ok(items);
    }
}
