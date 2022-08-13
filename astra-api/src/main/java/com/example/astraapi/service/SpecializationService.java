package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.SpecializationDto;

import java.util.List;

public interface SpecializationService {
  IdDto save(SpecializationDto specializationDto);

  List<SpecializationDto> getAll();

  List<SpecializationDto> getNotSelectedForExam(Long examId);
}
