package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestSubjectDto;
import com.example.astraapi.dto.ResponseSubjectDto;

import java.util.List;

public interface SubjectService {
  IdDto save(RequestSubjectDto requestSubjectDto);

  List<ResponseSubjectDto> getAll();

  void update(Long id, RequestSubjectDto requestSubjectDto);
}
