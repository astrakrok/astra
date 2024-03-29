package com.example.astraapi.controller;

import com.example.astraapi.dto.exam.ResponseExamDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoint.EXAMS)
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;

    @GetMapping
    public ResponseEntity<List<ResponseExamDto>> getAll() {
        List<ResponseExamDto> items = examService.getAll();
        return ResponseEntity.ok(items);
    }
}
