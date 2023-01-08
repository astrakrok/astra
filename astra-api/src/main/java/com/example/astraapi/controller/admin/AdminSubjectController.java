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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.ADMIN_SUBJECTS)
@RequiredArgsConstructor
public class AdminSubjectController {
    private final SubjectService service;

    @PostMapping
    public IdDto saveSubject(@RequestBody @Valid RequestSubjectDto requestSubjectDto) {
        return service.save(requestSubjectDto);
    }

    @PostMapping("/details")
    public Page<ResponseSubjectDto> search(
            @RequestBody AdminSubjectFilterDto filter,
            Pageable pageable
    ) {
        return service.search(filter, pageable);
    }

    @PutMapping("/{id}")
    public void updateSubject(
            @PathVariable("id") Long id,
            @Valid @RequestBody RequestSubjectDto subjectDto
    ) {
        service.update(id, subjectDto);
    }
}
