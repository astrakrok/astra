package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminSubjectFilterDto;
import com.example.astraapi.dto.subject.RequestSubjectDto;
import com.example.astraapi.dto.subject.ResponseSubjectDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.ADMIN_SUBJECTS)
@RequiredArgsConstructor
public class AdminSubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<IdDto> saveSubject(@RequestBody @Valid RequestSubjectDto requestSubjectDto) {
        IdDto idDto = subjectService.save(requestSubjectDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(idDto);
    }

    @PostMapping("/details")
    public ResponseEntity<Page<ResponseSubjectDto>> search(
            @RequestBody AdminSubjectFilterDto filter,
            Pageable pageable
    ) {
        Page<ResponseSubjectDto> page = subjectService.search(filter, pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSubject(
            @PathVariable("id") Long id,
            @Valid @RequestBody RequestSubjectDto subjectDto
    ) {
        subjectService.update(id, subjectDto);
        return ResponseEntity.ok().build();
    }
}
