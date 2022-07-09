package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.ResponseSubjectDto;
import com.example.astraapi.dto.RequestSubjectDto;

import java.util.List;

public interface SubjectService {
  IdDto save(Long specializationId, RequestSubjectDto requestSubjectDto);

  List<ResponseSubjectDto> getAllBySpecializationId(Long specializationId);
}
