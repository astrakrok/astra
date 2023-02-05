package com.example.astraapi.service;

import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.dto.filter.AdminImportTestFilterDto;
import com.example.astraapi.dto.importing.FileImportDto;
import com.example.astraapi.dto.importing.ImportStatsDto;
import com.example.astraapi.dto.importing.WebImportDto;
import com.example.astraapi.dto.test.RequestTestDto;
import com.example.astraapi.dto.test.TestFullDetailDto;
import com.example.astraapi.entity.ImportTestEntity;
import com.example.astraapi.entity.projection.ImportStatsProjection;
import com.example.astraapi.entity.projection.ImportSubjectProjection;
import com.example.astraapi.factory.TransferFactory;
import com.example.astraapi.mapper.ImportMapper;
import com.example.astraapi.mapper.TestMapper;
import com.example.astraapi.mapper.TestVariantMapper;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import com.example.astraapi.meta.ImportSource;
import com.example.astraapi.meta.TestStatus;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.model.importing.ImportResult;
import com.example.astraapi.model.importing.ImportSubject;
import com.example.astraapi.model.importing.ImportTest;
import com.example.astraapi.model.importing.ImportVariant;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.repository.ImportRepository;
import com.example.astraapi.repository.ImportTestRepository;
import com.example.astraapi.repository.SubjectRepository;
import com.example.astraapi.service.impl.TransferServiceImpl;
import com.example.astraapi.validation.ErrorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {
    @InjectMocks
    private TransferServiceImpl transferService;
    @Mock
    private TransferFactory transferFactory;
    @Mock
    private TestMapper testMapper;
    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private ImportRepository importRepository;
    @Mock
    private ImportTestRepository importTestRepository;
    @Mock
    private ErrorValidator<RequestTestDto> testValidator;
    @Mock
    private TestService testService;
    @Mock
    private ImportMapper importMapper;

    @BeforeEach
    public void beforeEach() {
        setupImportMapper();
        setupTestMapper();
    }

    @Test
    void shouldImportTestsFromFile() {
        List<ImportTestEntity> importTestEntities = new ArrayList<>();

        ImportResult importResult = new ImportResult(
                ImportSource.CSV_FILE,
                "import.csv",
                List.of(
                        new ImportTest(
                                "question",
                                "comment",
                                List.of(
                                        new ImportSubject("subject 1", null, null),
                                        new ImportSubject("subject 2", null, null),
                                        new ImportSubject("subject 3", "specialization 3", "step 3")),
                                List.of(
                                        new ImportVariant("title 1", "explanation 1", true),
                                        new ImportVariant("title 2", "explanation 2", false),
                                        new ImportVariant("title 3", "explanation 3", false),
                                        new ImportVariant("title 4", "explanation 4", false))),
                        new ImportTest(
                                "question another",
                                "comment another",
                                List.of(
                                        new ImportSubject("subject mock 1", "mock specialization 1", null),
                                        new ImportSubject("subject mock 2", null, null),
                                        new ImportSubject("subject mock 3", "specialization 3", "step 3")),
                                List.of(
                                        new ImportVariant("title 1", "explanation 1", false),
                                        new ImportVariant("title 2", "explanation 2", false),
                                        new ImportVariant("title 3", "explanation 3", true),
                                        new ImportVariant("title 4", "explanation 4", false))),
                        new ImportTest(
                                "mock question",
                                "mock comment",
                                List.of(
                                        new ImportSubject("test subject 1", null, null),
                                        new ImportSubject("test subject 2", null, null),
                                        new ImportSubject("test subject 3", "test specialization 3", "test step 3")),
                                List.of(
                                        new ImportVariant("title 1", "explanation 1", false),
                                        new ImportVariant("title 2", "explanation 2", true),
                                        new ImportVariant("title 3", "explanation 3", false),
                                        new ImportVariant("title 4", "explanation 4", false)))),
                new HashMap<>());

        FileImporter fileImporter = mock(FileImporter.class);
        when(fileImporter.importTests(any())).thenReturn(importResult);
        when(transferFactory.getFileImporter(any())).thenReturn(fileImporter);
        when(subjectRepository.getSubjects(any(), any(), any())).thenReturn(List.of(
                new ImportSubjectProjection(1L, "subject 1", 2L, "specialization 1", 3L, "step title", "subject 1"),
                new ImportSubjectProjection(2L, "subject 2", 3L, "specialization 2", 4L, "step title", "subject 2"),
                new ImportSubjectProjection(1L, "subject 1", 4L, "specialization 3", 3L, "step title", "subject 1"),
                new ImportSubjectProjection(null, null, null, null, null, null, "subject 4"),
                new ImportSubjectProjection(10L, "subject 5", 20L, "specialization 20", 20L, "mock step title", "subject 5")
        ));
        when(testValidator.validate(any())).thenReturn(Collections.emptyList());
        doAnswer(invocation -> {
            importTestEntities.addAll(invocation.getArgument(0));
            return null;
        }).when(importTestRepository).saveAll(any());
        when(testService.save(any())).thenReturn(new TestFullDetailDto(5L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()));

        transferService.importFromFile(new FileImportDto("title", mock(MultipartFile.class)));

        verify(importRepository, times(1)).save(any());
        verify(testService, times(3)).save(any());
        assertEquals(3, importTestEntities.size());
        List<ValidationError> errorsList = (List<ValidationError>) importTestEntities.get(0).getDetails().get("errors");
        assertEquals(2, errorsList.size());
        assertEquals(ValidationErrorType.NOT_FOUND, errorsList.get(0).getType());
        assertEquals(ValidationErrorType.CONFLICT, errorsList.get(1).getType());
    }

    @Test
    void shouldImportTestsFromWeb() {
        List<ImportTestEntity> importTestEntities = new ArrayList<>();

        ImportResult importResult = new ImportResult(
                ImportSource.CSV_FILE,
                "import.csv",
                List.of(
                        new ImportTest(
                                "question",
                                "comment",
                                List.of(
                                        new ImportSubject("subject 1", null, null),
                                        new ImportSubject("subject 2", null, null),
                                        new ImportSubject("subject 3", "specialization 3", "step 3")),
                                List.of(
                                        new ImportVariant("title 1", "explanation 1", true),
                                        new ImportVariant("title 2", "explanation 2", false),
                                        new ImportVariant("title 3", "explanation 3", false),
                                        new ImportVariant("title 4", "explanation 4", false))),
                        new ImportTest(
                                "question another",
                                "comment another",
                                List.of(
                                        new ImportSubject("subject mock 1", "mock specialization 1", null),
                                        new ImportSubject("subject mock 2", null, null),
                                        new ImportSubject("subject mock 3", "specialization 3", "step 3")),
                                List.of(
                                        new ImportVariant("title 1", "explanation 1", false),
                                        new ImportVariant("title 2", "explanation 2", false),
                                        new ImportVariant("title 3", "explanation 3", true),
                                        new ImportVariant("title 4", "explanation 4", false))),
                        new ImportTest(
                                "mock question",
                                "mock comment",
                                List.of(
                                        new ImportSubject("test subject 1", null, null),
                                        new ImportSubject("test subject 2", null, null),
                                        new ImportSubject("test subject 3", "test specialization 3", "test step 3")),
                                List.of(
                                        new ImportVariant("title 1", "explanation 1", false),
                                        new ImportVariant("title 2", "explanation 2", true),
                                        new ImportVariant("title 3", "explanation 3", false),
                                        new ImportVariant("title 4", "explanation 4", false)))),
                new HashMap<>());

        WebImporter webImporter = mock(WebImporter.class);
        when(webImporter.importTests(any())).thenReturn(importResult);
        when(transferFactory.getWebImporter(any())).thenReturn(webImporter);
        when(subjectRepository.getSubjects(any(), any(), any())).thenReturn(List.of(
                new ImportSubjectProjection(1L, "subject 1", 2L, "specialization 1", 3L, "step title", "subject 1"),
                new ImportSubjectProjection(2L, "subject 2", 3L, "specialization 2", 4L, "step title", "subject 2"),
                new ImportSubjectProjection(1L, "subject 1", 4L, "specialization 3", 3L, "step title", "subject 1"),
                new ImportSubjectProjection(null, null, null, null, null, null, "subject 4"),
                new ImportSubjectProjection(10L, "subject 5", 20L, "specialization 20", 20L, "mock step title", "subject 5")
        ));
        when(testValidator.validate(any())).thenReturn(Collections.emptyList());
        doAnswer(invocation -> {
            importTestEntities.addAll(invocation.getArgument(0));
            return null;
        }).when(importTestRepository).saveAll(any());
        when(testService.save(any())).thenReturn(new TestFullDetailDto(5L, null, null, null, null, TestStatus.ACTIVE, LocalDateTime.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap()));

        transferService.importFromWeb(new WebImportDto("title", "url"));

        verify(importRepository, times(1)).save(any());
        verify(testService, times(3)).save(any());
        assertEquals(3, importTestEntities.size());
        List<ValidationError> errorsList = (List<ValidationError>) importTestEntities.get(0).getDetails().get("errors");
        assertEquals(2, errorsList.size());
        assertEquals(ValidationErrorType.NOT_FOUND, errorsList.get(0).getType());
        assertEquals(ValidationErrorType.CONFLICT, errorsList.get(1).getType());
    }

    @Test
    void shouldReturnImportStatsPage() {
        Page<ImportStatsProjection> page = new Page<>();
        page.setPageSize(2);
        page.setRows(5L);
        page.setItems(List.of(
                new ImportStatsProjection(),
                new ImportStatsProjection()
        ));

        when(importRepository.getStats(any(), any())).thenReturn(page);

        Page<ImportStatsDto> returnedPage = transferService.search(new AdminImportTestFilterDto(), new Pageable(2L, 0L));

        assertEquals(2, returnedPage.getPageSize());
        assertEquals(3, returnedPage.getPagesCount());
        assertEquals(5, returnedPage.getRows());
        assertEquals(2, returnedPage.getItems().size());
    }

    @Test
    void shouldReturnByteArrayOnTestsExport() {
        FileExporter fileExporter = mock(FileExporter.class);
        when(fileExporter.exportTests(any())).thenReturn(new byte[]{1, 2, 3});
        when(transferFactory.getFileExporter(any())).thenReturn(fileExporter);

        byte[] bytes = transferService.exportTests(new ExportDto());

        assertEquals(3, bytes.length);
        assertEquals(1, bytes[0]);
        assertEquals(2, bytes[1]);
        assertEquals(3, bytes[2]);
    }

    private void setupImportMapper() {
        ImportMapper mapper = Mappers.getMapper(ImportMapper.class);

        lenient().when(importMapper.toDto(any())).thenAnswer(invocation -> mapper.toDto(invocation.getArgument(0)));
    }

    private void setupTestMapper() {
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
}
