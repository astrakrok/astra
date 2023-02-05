package com.example.astraapi.service;

import com.example.astraapi.dto.TrainingSearchDto;
import com.example.astraapi.dto.examination.ExaminationSearchDto;
import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.dto.filter.AdminAvailableTestingTestsFilterDto;
import com.example.astraapi.dto.filter.AdminTestFilterDto;
import com.example.astraapi.dto.test.*;
import com.example.astraapi.dto.test.variant.TestVariantDto;
import com.example.astraapi.entity.TestEntity;
import com.example.astraapi.entity.projection.TestFullDetailProjection;
import com.example.astraapi.entity.projection.TestShortDetailProjection;
import com.example.astraapi.entity.projection.exporting.ExportTestProjection;
import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.mapper.TestMapper;
import com.example.astraapi.mapper.TestVariantMapper;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import com.example.astraapi.meta.TestStatus;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.model.exporting.ExportTest;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.repository.TestRepository;
import com.example.astraapi.service.impl.TestServiceImpl;
import com.example.astraapi.validation.ErrorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceTest {
    @InjectMocks
    private TestServiceImpl testService;
    @Mock
    private TestMapper testMapper;
    @Mock
    private TestRepository testRepository;
    @Mock
    private ErrorValidator<RequestTestDto> testValidator;
    @Mock
    private TestVariantService testVariantService;
    @Mock
    private TestSubjectService testSubjectService;
    @Mock
    private AdaptiveTestService adaptiveTestService;

    @BeforeEach
    public void beforeEach() {
        TestMapper mapper = Mappers.getMapper(TestMapper.class);
        TestVariantMapper testVariantMapper = Mappers.getMapper(TestVariantMapper.class);
        TitleQualifier titleQualifier = new TitleQualifier();
        ReflectionTestUtils.setField(testVariantMapper, "titleQualifier", titleQualifier);
        ReflectionTestUtils.setField(mapper, "titleQualifier", titleQualifier);
        ReflectionTestUtils.setField(mapper, "testVariantMapper", testVariantMapper);

        lenient().when(testMapper.toEntity(any(), any()))
                .thenAnswer(invocation -> mapper.toEntity(invocation.getArgument(0), invocation.getArgument(1)));
        lenient().when(testMapper.toEntity(any(), any(), any()))
                .thenAnswer(invocation -> mapper.toEntity(invocation.getArgument(0), invocation.getArgument(1), invocation.getArgument(2)));
        lenient().when(testMapper.toShortDetailDto(any()))
                .thenAnswer(invocation -> mapper.toShortDetailDto(invocation.getArgument(0)));
        lenient().when(testMapper.toFullDetailDto(any()))
                .thenAnswer(invocation -> mapper.toFullDetailDto(invocation.getArgument(0)));
        lenient().when(testMapper.toTrainingDto(any()))
                .thenAnswer(invocation -> mapper.toTrainingDto(invocation.getArgument(0)));
        lenient().when(testMapper.toExaminationDto(any()))
                .thenAnswer(invocation -> mapper.toExaminationDto(invocation.getArgument(0)));
        lenient().when(testMapper.toShortTestDto(any()))
                .thenAnswer(invocation -> mapper.toShortTestDto(invocation.getArgument(0)));
        lenient().when(testMapper.toRequestTestDto(any(), any()))
                .thenAnswer(invocation -> mapper.toRequestTestDto(invocation.getArgument(0), invocation.getArgument(1)));
        lenient().when(testMapper.toExportTest(any()))
                .thenAnswer(invocation -> mapper.toExportTest(invocation.getArgument(0)));
        lenient().when(testMapper.toAdaptiveTestDto(any()))
                .thenAnswer(invocation -> mapper.toAdaptiveTestDto(invocation.getArgument(0)));
    }

    @Test
    void shouldSaveActiveTest() {
        when(testValidator.validate(any())).thenReturn(Collections.emptyList());

        testService.save(new RequestTestDto(
                null,
                "question",
                "comment",
                null,
                null,
                List.of(
                        new TestVariantDto(null, null, "title 1", null, "explanation 1", null, false),
                        new TestVariantDto(null, null, "title 2", null, "explanation 2", null, false),
                        new TestVariantDto(null, null, "title 3", null, "explanation 3", null, true),
                        new TestVariantDto(null, null, "title 4", null, "explanation 4", null, false)
                ),
                Set.of(1L, 3L, 2L)
        ));

        verify(testRepository, times(1)).save(any());
        verify(testSubjectService, times(1)).save(any(), any());
        verify(testVariantService, times(1)).save(any(), any());
    }

    @Test
    void shouldThrowExceptionWhenValidationErrors() {
        when(testValidator.validate(any())).thenReturn(List.of(
                new ValidationError(ValidationErrorType.INVALID_CORRECTNESS)
        ));

        ValidationException exception = assertThrows(ValidationException.class, () -> testService.save(new RequestTestDto(
                null,
                "question",
                "comment",
                null,
                null,
                List.of(
                        new TestVariantDto(null, null, "title 1", null, "explanation 1", null, false)
                ),
                Set.of(1L, 3L, 2L)
        )));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.INVALID_CORRECTNESS, exception.getErrors().get(0).getType());

        verify(testRepository, never()).save(any());
        verify(testSubjectService, never()).save(any(), any());
        verify(testVariantService, never()).save(any(), any());
    }

    @Test
    void shouldSaveDraftTest() {
        testService.saveDraft(new RequestTestDto(
                null,
                "question",
                "comment",
                null,
                null,
                List.of(
                        new TestVariantDto(null, null, "title 1", null, "explanation 1", null, false),
                        new TestVariantDto(null, null, "title 2", null, "explanation 2", null, false),
                        new TestVariantDto(null, null, "title 3", null, "explanation 3", null, true),
                        new TestVariantDto(null, null, "title 4", null, "explanation 4", null, false)
                ),
                Set.of(1L, 3L, 2L)
        ));

        verify(testRepository, times(1)).save(any());
        verify(testSubjectService, times(1)).save(any(), any());
        verify(testVariantService, times(1)).save(any(), any());
    }

    @Test
    void shouldUpdateActiveTest() {
        when(testValidator.validate(any())).thenReturn(Collections.emptyList());

        testService.update(5L, new RequestTestDto(
                null,
                "question",
                "comment",
                null,
                null,
                List.of(
                        new TestVariantDto(null, null, "title 1", null, "explanation 1", null, false),
                        new TestVariantDto(null, null, "title 2", null, "explanation 2", null, false),
                        new TestVariantDto(null, null, "title 3", null, "explanation 3", null, true),
                        new TestVariantDto(null, null, "title 4", null, "explanation 4", null, false)
                ),
                Set.of(1L, 3L, 2L)
        ));

        verify(testRepository, times(1)).update(any());
        verify(testSubjectService, times(1)).update(any(), any());
        verify(testVariantService, times(1)).update(any(), any());
    }

    @Test
    void shouldNotUpdateActiveTestWhenValidationErrors() {
        when(testValidator.validate(any())).thenReturn(List.of(new ValidationError(ValidationErrorType.INVALID_CORRECTNESS)));

        ValidationException exception = assertThrows(ValidationException.class, () -> testService.update(5L, new RequestTestDto(
                null,
                "question",
                "comment",
                null,
                null,
                List.of(
                        new TestVariantDto(null, null, "title 1", null, "explanation 1", null, false)
                ),
                Set.of(1L, 3L, 2L)
        )));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.INVALID_CORRECTNESS, exception.getErrors().get(0).getType());

        verify(testRepository, never()).update(any());
        verify(testSubjectService, never()).update(any(), any());
        verify(testVariantService, never()).update(any(), any());
    }

    @Test
    void shouldUpdateDraftTest() {
        when(testRepository.existsByIdAndStatus(any(), any())).thenReturn(true);

        testService.updateDraft(5L, new RequestTestDto(
                null,
                "question",
                "comment",
                null,
                null,
                List.of(
                        new TestVariantDto(null, null, "title 1", null, "explanation 1", null, false),
                        new TestVariantDto(null, null, "title 2", null, "explanation 2", null, false),
                        new TestVariantDto(null, null, "title 3", null, "explanation 3", null, true),
                        new TestVariantDto(null, null, "title 4", null, "explanation 4", null, false)
                ),
                Set.of(1L, 3L, 2L)
        ));

        verify(testRepository, times(1)).update(any());
        verify(testSubjectService, times(1)).update(any(), any());
        verify(testVariantService, times(1)).update(any(), any());
    }

    @Test
    void shouldNotUpdateActiveTestToDraftTest() {
        when(testRepository.existsByIdAndStatus(any(), any())).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> testService.updateDraft(5L, new RequestTestDto(
                null,
                "question",
                "comment",
                null,
                null,
                List.of(
                        new TestVariantDto(null, null, "title 1", null, "explanation 1", null, false),
                        new TestVariantDto(null, null, "title 2", null, "explanation 2", null, false),
                        new TestVariantDto(null, null, "title 3", null, "explanation 3", null, true),
                        new TestVariantDto(null, null, "title 4", null, "explanation 4", null, false)
                ),
                Set.of(1L, 3L, 2L)
        )));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.INVALID_STATUS, exception.getErrors().get(0).getType());

        verify(testRepository, never()).update(any());
        verify(testSubjectService, never()).update(any(), any());
        verify(testVariantService, never()).update(any(), any());
    }

    @Test
    void shouldFilterTestsPage() {
        Page<TestShortDetailProjection> page = new Page<>();
        page.setPageSize(2);
        page.setRows(5L);
        page.setItems(List.of(
                new TestShortDetailProjection(1L, "question", "comment", TestStatus.DRAFT, LocalDateTime.now(), Collections.emptyList()),
                new TestShortDetailProjection(2L, "another question", "another comment", TestStatus.DRAFT, LocalDateTime.now(), Collections.emptyList())
        ));

        when(testRepository.getAll(any(), any(), any(), any())).thenReturn(page);

        Page<TestShortDetailDto> returnedPage = testService.getAll(new AdminTestFilterDto(), new Pageable(2L, 0L));

        assertEquals(2, returnedPage.getPageSize());
        assertEquals(3, returnedPage.getPagesCount());
        assertEquals(5, returnedPage.getRows());
        assertEquals(2, returnedPage.getItems().size());
    }

    @Test
    void shouldReturnDetailedTest() {
        when(testRepository.getDetailedTestById(anyLong())).thenReturn(Optional.of(new TestFullDetailProjection()));

        Optional<TestFullDetailDto> test = testService.getDetailedTest(5L);

        assertTrue(test.isPresent());
    }

    @Test
    void shouldReturnTrainingTests() {
        when(testRepository.getTestsByTestingId(any(), any())).thenReturn(List.of(
                new TestFullDetailProjection(), new TestFullDetailProjection()
        ));

        List<TrainingTestDto> items = testService.getTrainingTests(new TrainingSearchDto());

        assertEquals(2, items.size());
    }

    @Test
    void shouldReturnExaminationTestsByCountAndSearch() {
        when(testRepository.getTestsByTestingId(any(), anyLong())).thenReturn(List.of(
                new TestFullDetailProjection(), new TestFullDetailProjection()
        ));

        List<ExaminationTestDto> items = testService.getExaminationTests(0, new ExaminationSearchDto());

        assertEquals(2, items.size());
    }

    @Test
    void shouldReturnExaminationTestsByIds() {
        when(testRepository.getTestsByIds(any())).thenReturn(List.of(
                new TestFullDetailProjection(), new TestFullDetailProjection()
        ));

        List<ExaminationTestDto> items = testService.getExaminationTests(List.of(1L, 2L));

        assertEquals(2L, items.size());
    }

    @Test
    void shouldReturnNotYetSelectedTestingTests() {
        Page<TestEntity> page = new Page<>();
        page.setPageSize(2);
        page.setRows(5L);
        page.setItems(List.of(
                new TestEntity(),
                new TestEntity()
        ));

        when(testRepository.getNotRelatedTestingTests(any(), any(), any())).thenReturn(page);

        Page<TestingShortTestDto> returnedPage = testService.getNotYetSelectedTestingTests(5L, new AdminAvailableTestingTestsFilterDto(), new Pageable(2L, 0L));

        assertEquals(2, returnedPage.getPageSize());
        assertEquals(3, returnedPage.getPagesCount());
        assertEquals(5, returnedPage.getRows());
        assertEquals(2, returnedPage.getItems().size());
    }

    @Test
    void shouldDeleteTest() {
        when(testRepository.existsByIdAndStatus(any(), any())).thenReturn(true);

        testService.deleteTest(5L);

        verify(testRepository, times(1)).deleteById(any());
    }

    @Test
    void shouldNotDeleteActiveTest() {
        when(testRepository.existsByIdAndStatus(any(), any())).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> testService.deleteTest(5L));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.INVALID_STATUS, exception.getErrors().get(0).getType());
    }

    @Test
    void shouldReturnTestsForExport() {
        Page<ExportTestProjection> page = new Page<>();
        page.setPageSize(2);
        page.setRows(5L);
        page.setItems(List.of(
                new ExportTestProjection(),
                new ExportTestProjection()
        ));

        when(testRepository.getExportTests(any(), any())).thenReturn(page);

        Page<ExportTest> returnedPage = testService.getTestsForExport(new ExportDto(), new Pageable(2L, 0L));

        assertEquals(2, returnedPage.getPageSize());
        assertEquals(3, returnedPage.getPagesCount());
        assertEquals(5, returnedPage.getRows());
        assertEquals(2, returnedPage.getItems().size());
    }

    @Test
    void shouldReturnAdaptiveTests() {
        when(adaptiveTestService.getAdaptiveTests(anyLong())).thenReturn(List.of(
                new AdaptiveTestDto(), new AdaptiveTestDto(), new AdaptiveTestDto()
        ));

        List<AdaptiveTestDto> items = testService.getAdaptiveTests(5L);

        assertEquals(3L, items.size());
    }
}
