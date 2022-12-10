package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.subject.RequestSubjectDto;
import com.example.astraapi.dto.subject.ResponseSubjectDto;

import java.util.List;

public interface SubjectService {
  IdDto save(RequestSubjectDto requestSubjectDto);

  List<ResponseSubjectDto> getAllSubjectsDetails();

  void update(Long id, RequestSubjectDto requestSubjectDto);
}
