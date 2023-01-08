package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.exam.RequestExamDto;
import com.example.astraapi.dto.exam.ResponseExamDto;
import com.example.astraapi.dto.specialization.StepSpecializationDto;

import java.util.List;

public interface ExamService {
  IdDto save(RequestExamDto examDto);

  List<ResponseExamDto> getAll();

  List<ResponseExamDto> getActive(Long specializationId);

  void delete(Long id);

  void update(Long id, RequestExamDto examDto);

  List<StepSpecializationDto> getAvailableSpecializations(Long id);
}
