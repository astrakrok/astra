package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.SubjectDto;

public interface SubjectService {
  IdDto save(Long specializationId, SubjectDto subjectDto);
}
