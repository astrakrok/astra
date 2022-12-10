package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.specialization.RequestSpecializationDto;
import com.example.astraapi.dto.specialization.SpecializationDto;
import com.example.astraapi.dto.specialization.StepSpecializationDto;

import java.util.List;

public interface SpecializationService {
  IdDto save(SpecializationDto specializationDto);

  IdDto save(Long stepId, RequestSpecializationDto specializationDto);

  List<StepSpecializationDto> getAll();

  List<SpecializationDto> getAll(Long stepId);

  List<SpecializationDto> getNotSelectedForExam(Long examId);
}
