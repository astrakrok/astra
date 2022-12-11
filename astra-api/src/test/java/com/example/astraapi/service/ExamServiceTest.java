package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.exam.RequestExamDto;
import com.example.astraapi.dto.exam.ResponseExamDto;
import com.example.astraapi.dto.specialization.StepSpecializationDto;
import com.example.astraapi.dto.step.StepDto;
import com.example.astraapi.entity.ExamEntity;
import com.example.astraapi.mapper.ExamMapper;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import com.example.astraapi.repository.ExamRepository;
import com.example.astraapi.service.impl.ExamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ExamServiceTest {
  @InjectMocks
  private ExamServiceImpl examService;
  @Mock
  private SpecializationService specializationService;
  @Spy
  private ExamMapper examMapper;
  @Mock
  private ExamRepository examRepository;

  @BeforeEach
  void beforeEach() {
    ExamMapper mapper = Mappers.getMapper(ExamMapper.class);
    TitleQualifier titleQualifier = new TitleQualifier();
    ReflectionTestUtils.setField(mapper, "titleQualifier", titleQualifier);

    Mockito.lenient().when(examMapper.toDto(ArgumentMatchers.any())).thenAnswer(invocation -> {
      ExamEntity entity = invocation.getArgument(0);
      return mapper.toDto(entity);
    });

    Mockito.lenient().when(examMapper.toEntity(ArgumentMatchers.any())).thenAnswer(invocation -> {
      RequestExamDto dto = invocation.getArgument(0);
      return mapper.toEntity(dto);
    });
  }

  @Test
  void shouldSaveExam() {
    RequestExamDto examDto = new RequestExamDto("Testing");

    Mockito.doAnswer(invocation -> {
      ExamEntity entity = invocation.getArgument(0);
      entity.setId(10L);
      return null;
    }).when(examRepository).save(ArgumentMatchers.any());

    IdDto idDto = examService.save(examDto);

    assertEquals(10L, idDto.getId());
  }

  @Test
  void shouldReturnAllExams() {
    Mockito.when(examRepository.getAll()).thenReturn(List.of(
        new ExamEntity(1L, "2010"),
        new ExamEntity(2L, "2011"),
        new ExamEntity(3L, "2012"),
        new ExamEntity(4L, "2013")
    ));

    List<ResponseExamDto> exams = examService.getAll();

    assertEquals(4, exams.size());
    assertEquals("2010", exams.get(0).getTitle());
    assertEquals("2011", exams.get(1).getTitle());
    assertEquals("2012", exams.get(2).getTitle());
    assertEquals("2013", exams.get(3).getTitle());
  }

  @Test
  void shouldReturnAllBySpecializationId() {
    Mockito.when(examRepository.getAllBySpecializationId(ArgumentMatchers.any())).thenReturn(List.of(
        new ExamEntity(1L, "2010"),
        new ExamEntity(2L, "2011"),
        new ExamEntity(3L, "2012"),
        new ExamEntity(4L, "2013")
    ));

    List<ResponseExamDto> exams = examService.getAll(5L);

    assertEquals(4, exams.size());
    assertEquals("2010", exams.get(0).getTitle());
    assertEquals("2011", exams.get(1).getTitle());
    assertEquals("2012", exams.get(2).getTitle());
    assertEquals("2013", exams.get(3).getTitle());
  }

  @Test
  void shouldDeleteExam() {
    Long[] id = new Long[1];
    Mockito.doAnswer(invocation -> {
      Long passedId = invocation.getArgument(0);
      id[0] = passedId;
      return null;
    }).when(examRepository).deleteById(ArgumentMatchers.any());

    examService.delete(5L);

    assertEquals(5L, id[0]);
  }

  @Test
  void shouldUpdateExam() {
    Long[] id = new Long[1];
    ExamEntity[] entity = new ExamEntity[1];

    Mockito.doAnswer(invocation -> {
      Long passedId = invocation.getArgument(0);
      ExamEntity passedEntity = invocation.getArgument(1);

      id[0] = passedId;
      entity[0] = passedEntity;
      return null;
    }).when(examRepository).updateById(ArgumentMatchers.any(), ArgumentMatchers.any());

    examService.update(5L, new RequestExamDto("Example title"));

    assertEquals(5L, id[0]);
    assertEquals("Example title", entity[0].getTitle());
  }

  @Test
  void shouldReturnNotSelectedSpecializations() {
    Mockito.when(specializationService.getNotSelectedForExam(ArgumentMatchers.any())).thenReturn(List.of(
        new StepSpecializationDto(1L, "Spec 1", new StepDto()),
        new StepSpecializationDto(1L, "Spec 2", new StepDto()),
        new StepSpecializationDto(1L, "Spec 3", new StepDto())
    ));

    List<StepSpecializationDto> availableSpecializations = examService.getAvailableSpecializations(6L);

    assertEquals(3, availableSpecializations.size());
    assertEquals("Spec 1", availableSpecializations.get(0).getTitle());
    assertEquals("Spec 2", availableSpecializations.get(1).getTitle());
    assertEquals("Spec 3", availableSpecializations.get(2).getTitle());
  }
}
