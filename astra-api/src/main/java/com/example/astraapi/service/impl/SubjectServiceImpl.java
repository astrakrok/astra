package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminSubjectFilterDto;
import com.example.astraapi.dto.subject.RequestSubjectDto;
import com.example.astraapi.dto.subject.ResponseSubjectDto;
import com.example.astraapi.entity.SubjectEntity;
import com.example.astraapi.mapper.SubjectMapper;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.repository.SubjectRepository;
import com.example.astraapi.service.SubjectService;
import com.example.astraapi.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    @Transactional
    public IdDto save(RequestSubjectDto requestSubjectDto) {
        SubjectEntity subjectEntity = subjectMapper.toEntity(requestSubjectDto);
        subjectRepository.save(subjectEntity);
        Long subjectId = subjectEntity.getId();
        return new IdDto(subjectId);
    }

    @Override
    public Page<ResponseSubjectDto> search(AdminSubjectFilterDto filter, Pageable pageable) {
        return PageUtils.mapPage(
                subjectRepository.search(filter.getStepId(), filter.getSpecializationId(), filter.getSearchText(), pageable),
                subjectMapper::toDto);
    }

    @Override
    @Transactional
    public void update(Long id, RequestSubjectDto requestSubjectDto) {
        SubjectEntity subjectEntity = subjectMapper.toEntity(requestSubjectDto);
        subjectRepository.update(id, subjectEntity);
    }
}
