package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestExamDto;
import com.example.astraapi.dto.ResponseExamDto;

import java.util.List;

public interface ExamService {
  IdDto save(RequestExamDto examDto);

  List<ResponseExamDto> getAll();

  void delete(Long id);

  void update(Long id, RequestExamDto examDto);
}
