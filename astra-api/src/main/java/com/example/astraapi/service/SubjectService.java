package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminSubjectFilterDto;
import com.example.astraapi.dto.subject.RequestSubjectDto;
import com.example.astraapi.dto.subject.ResponseSubjectDto;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;

public interface SubjectService {
  IdDto save(RequestSubjectDto requestSubjectDto);

  Page<ResponseSubjectDto> search(AdminSubjectFilterDto filter, Pageable pageable);

  void update(Long id, RequestSubjectDto requestSubjectDto);
}
