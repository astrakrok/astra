package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.SubjectAndSpecializationIdDto;
import com.example.astraapi.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
  IdDto save(Long specializationId, SubjectDto subjectDto);

  List<SubjectAndSpecializationIdDto> getAll(Long specializationId);
}
