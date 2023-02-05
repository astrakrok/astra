package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminSubjectFilterDto;
import com.example.astraapi.dto.subject.RequestSubjectDto;
import com.example.astraapi.dto.subject.ResponseSubjectDto;
import com.example.astraapi.entity.SubjectEntity;
import com.example.astraapi.entity.projection.SubjectDetailProjection;
import com.example.astraapi.mapper.SubjectMapper;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.repository.SubjectRepository;
import com.example.astraapi.service.impl.SubjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {
    @InjectMocks
    private SubjectServiceImpl subjectService;
    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private SubjectMapper subjectMapper;

    @BeforeEach
    public void beforeEach() {
        SubjectMapper mapper = Mappers.getMapper(SubjectMapper.class);
        ReflectionTestUtils.setField(mapper, "titleQualifier", new TitleQualifier());

        lenient().when(subjectMapper.toEntity(any())).thenAnswer(invocation -> mapper.toEntity(invocation.getArgument(0)));
        lenient().when(subjectMapper.toEntity(any(), any())).thenAnswer(invocation -> mapper.toEntity(invocation.getArgument(0), invocation.getArgument(1)));
        lenient().when(subjectMapper.toDto(any())).thenAnswer(invocation -> mapper.toDto(invocation.getArgument(0)));
        lenient().when(subjectMapper.toStatisticDto(any())).thenAnswer(invocation -> mapper.toStatisticDto(invocation.getArgument(0)));
    }

    @Test
    void shouldSaveSubject() {
        doAnswer(invocation -> {
            SubjectEntity entity = invocation.getArgument(0);
            entity.setId(5L);
            return entity;
        }).when(subjectRepository).save(any());

        IdDto idDto = subjectService.save(new RequestSubjectDto(5L, "some title"));

        assertEquals(5L, idDto.getId());
    }

    @Test
    void shouldSearchSubjects() {
        Page<SubjectDetailProjection> page = new Page<>();
        page.setItems(List.of(
                new SubjectDetailProjection(1L, "title 1", null),
                new SubjectDetailProjection(2L, "title 2", null),
                new SubjectDetailProjection(3L, "title 3", null),
                new SubjectDetailProjection(4L, "title 4", null)
        ));
        page.setPageSize(4);
        page.setRows(100);
        Pageable pageable = new Pageable();
        pageable.setPageNumber(0L);
        pageable.setPageSize(4L);

        when(subjectRepository.search(any(), any(), any(), any())).thenReturn(page);

        Page<ResponseSubjectDto> returnedPage = subjectService.search(new AdminSubjectFilterDto(1L, 2L, "searchText"), pageable);

        assertEquals(4, returnedPage.getItems().size());
        assertEquals(100, returnedPage.getRows());
        assertEquals(4, returnedPage.getPageSize());
    }

    @Test
    void shouldUpdateSubject() {
        Long[] subjectId = new Long[1];
        doAnswer(invocation -> {
            SubjectEntity entity = invocation.getArgument(0);
            subjectId[0] = entity.getId();
            return null;
        }).when(subjectRepository).update(any());

        subjectService.update(6L, new RequestSubjectDto(2L, "title"));

        assertEquals(6L, subjectId[0]);
    }
}
