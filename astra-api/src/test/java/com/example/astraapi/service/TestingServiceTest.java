package com.example.astraapi.service;

import com.example.astraapi.dto.filter.AdminAvailableTestingTestsFilterDto;
import com.example.astraapi.dto.filter.AdminTestingTestsFilterDto;
import com.example.astraapi.dto.test.RequestTestingDetailTestDto;
import com.example.astraapi.dto.test.TestingShortTestDto;
import com.example.astraapi.dto.test.TestingTestQuestionDto;
import com.example.astraapi.dto.testing.*;
import com.example.astraapi.entity.TestingEntity;
import com.example.astraapi.entity.TestingWithSpecializationEntity;
import com.example.astraapi.entity.projection.TestingInfoProjection;
import com.example.astraapi.entity.projection.TestingTestSimpleProjection;
import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.mapper.TestingMapper;
import com.example.astraapi.meta.ConfigProperty;
import com.example.astraapi.meta.TestingStatus;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.model.TestingPage;
import com.example.astraapi.repository.TestingRepository;
import com.example.astraapi.service.impl.TestingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestingServiceTest {
    @InjectMocks
    private TestingServiceImpl testingService;
    @Mock
    private TestService testService;
    @Mock
    private TestingMapper testingMapper;
    @Mock
    private PropertyService propertyService;
    @Mock
    private TestingRepository testingRepository;

    @BeforeEach
    public void beforeEach() {
        TestingMapper mapper = Mappers.getMapper(TestingMapper.class);

        lenient().when(testingMapper.toEntity(any(RequestTestingDto.class), any()))
                .thenAnswer(invocation -> mapper.toEntity(invocation.<RequestTestingDto>getArgument(0), invocation.getArgument(1)));

        lenient().when(testingMapper.toEntity(any(RequestTestingDetailTestDto.class), any()))
                .thenAnswer(invocation -> mapper.toEntity(invocation.<RequestTestingDetailTestDto>getArgument(0), invocation.getArgument(1)));

        lenient().when(testingMapper.toDto(any(TestingWithSpecializationEntity.class)))
                .thenAnswer(invocation -> mapper.toDto(invocation.<TestingWithSpecializationEntity>getArgument(0)));

        lenient().when(testingMapper.toDto(any(TestingEntity.class)))
                .thenAnswer(invocation -> mapper.toDto(invocation.<TestingEntity>getArgument(0)));

        lenient().when(testingMapper.toDetailDto(any(TestingEntity.class)))
                .thenAnswer(invocation -> mapper.toDetailDto(invocation.<TestingEntity>getArgument(0)));

        lenient().when(testingMapper.toDetailDto(any(TestingInfoProjection.class)))
                .thenAnswer(invocation -> mapper.toDetailDto(invocation.<TestingInfoProjection>getArgument(0)));

        lenient().when(testingMapper.toInfoDto(any()))
                .thenAnswer(invocation -> mapper.toInfoDto(invocation.getArgument(0)));

        lenient().when(testingMapper.toTestQuestionDto(any()))
                .thenAnswer(invocation -> mapper.toTestQuestionDto(invocation.getArgument(0)));
    }

    @Test
    void shouldSaveTesting() {
        testingService.save(new RequestTestingDto(1L, 1L));

        verify(testingRepository, times(1)).save(any());
    }

    @Test
    void shouldReturnTestingsWithSpecialization() {
        when(testingRepository.getByExamIdWithSpecialization(any())).thenReturn(List.of(
                new TestingWithSpecializationEntity(1L, 2L, TestingStatus.ACTIVE, null),
                new TestingWithSpecializationEntity(3L, 4L, TestingStatus.DRAFT, null)
        ));

        List<TestingWithSpecializationDto> items = testingService.getWithSpecializations(5L);

        assertEquals(2, items.size());
    }

    @Test
    void shouldReturnTestingInfo() {
        when(testingRepository.findTestingInfoById(any())).thenReturn(Optional.of(new TestingInfoProjection(5L, null, TestingStatus.ACTIVE, null, null)));

        Optional<TestingInfoDto> item = testingService.getTestingInfo(5L);

        assertTrue(item.isPresent());
    }

    @Test
    void shouldReturnTestsQuestionsPage() {
        TestingPage<TestingTestSimpleProjection> testingPage = new TestingPage<>();
        testingPage.setTestingId(5L);
        testingPage.setPageSize(2L);
        testingPage.setRows(5L);
        testingPage.setItems(List.of(
                new TestingTestSimpleProjection(1L, 2L, "question 1"),
                new TestingTestSimpleProjection(3L, 5L, "question 2")
        ));

        when(testingRepository.getTestingTestsByTestingId(any(), any(), any())).thenReturn(testingPage);

        TestingPage<TestingTestQuestionDto> page = testingService.getTestsQuestions(5L, new AdminTestingTestsFilterDto(), new Pageable(2L, 0L));

        assertEquals(5L, page.getTestingId());
        assertEquals(3, page.getPagesCount());
        assertEquals(2, page.getPageSize());
        assertEquals(5, page.getRows());
        assertEquals(2, page.getItems().size());
    }

    @Test
    void shouldReturnEmptyPageOnNullResponseWhenReturningTestsQuestionsPage() {
        when(testingRepository.getTestingTestsByTestingId(any(), any(), any())).thenReturn(null);

        TestingPage<TestingTestQuestionDto> page = testingService.getTestsQuestions(5L, new AdminTestingTestsFilterDto(), new Pageable(2L, 0L));

        assertEquals(5L, page.getTestingId());
        assertEquals(0, page.getPagesCount());
        assertEquals(0, page.getPageSize());
        assertEquals(0, page.getRows());
        assertEquals(0, page.getItems().size());
    }

    @Test
    void shouldReturnNotSelectedTestingTests() {
        Page<TestingShortTestDto> page = new Page<>();
        page.setPageSize(2L);
        page.setRows(5L);
        page.setItems(List.of(
                new TestingShortTestDto(1L, "question 1", Collections.emptyList()),
                new TestingShortTestDto(2L, "question 2", Collections.emptyList())
        ));
        when(testService.getNotYetSelectedTestingTests(any(), any(), any())).thenReturn(page);

        Page<TestingShortTestDto> returnedPage = testingService.getNotSelectedTestingTests(5L, new AdminAvailableTestingTestsFilterDto(), new Pageable(2L, 0L));

        assertEquals(page, returnedPage);
    }

    @Test
    void shouldReturnDescriptions() {
        when(propertyService.getProperties(any())).thenReturn(Map.of(
                ConfigProperty.ADAPTIVE_DESCRIPTION.getName(), "adaptive description text",
                ConfigProperty.EXAMINATION_DESCRIPTION.getName(), "examination description text",
                ConfigProperty.TRAINING_DESCRIPTION.getName(), "training description text"
        ));

        TestingDescriptionDto dto = testingService.getDescription();

        assertEquals("adaptive description text", dto.getAdaptiveDescription());
        assertEquals("examination description text", dto.getExaminationDescription());
        assertEquals("training description text", dto.getTrainingDescription());
    }

    @Test
    void shouldReturnAvailableTestings() {
        when(testingRepository.getAvailable()).thenReturn(List.of(
                new TestingEntity(), new TestingEntity()
        ));

        List<TestingDetailDto> items = testingService.getAvailableTestings();

        assertEquals(2, items.size());
    }

    @Test
    void shouldReturnOneTesting() {
        when(testingRepository.getByExamIdAndSpecializationId(any(), any())).thenReturn(new TestingEntity(5L, 2L, 1L, TestingStatus.ACTIVE, null, null));

        TestingDto dto = testingService.getOne(2L, 1L);

        assertEquals(5L, dto.getId());
    }

    @Test
    void shouldUpdateStatusToDraft() {
        testingService.updateStatus(5L, new TestingStatusDto(TestingStatus.DRAFT));

        verify(testingRepository, times(1)).updateStatusById(any(), any());
    }

    @Test
    void shouldUpdateStatusToActive() {
        when(testingRepository.getTestsCount(any())).thenReturn(5L);

        testingService.updateStatus(5L, new TestingStatusDto(TestingStatus.ACTIVE));

        verify(testingRepository, times(1)).updateStatusById(any(), any());
    }

    @Test
    void shouldNotUpdateStatusToActiveForTestingWithNoTests() {
        when(testingRepository.getTestsCount(any())).thenReturn(0L);

        ValidationException exception = assertThrows(ValidationException.class, () -> testingService.updateStatus(5L, new TestingStatusDto(TestingStatus.ACTIVE)));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.EMPTY, exception.getErrors().get(0).getType());
    }
}
