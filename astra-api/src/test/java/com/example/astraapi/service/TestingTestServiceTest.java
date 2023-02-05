package com.example.astraapi.service;

import com.example.astraapi.dto.test.RequestTestingDetailTestDto;
import com.example.astraapi.dto.test.RequestTestingTestDto;
import com.example.astraapi.dto.testing.RequestTestingDto;
import com.example.astraapi.dto.testing.TestingDetailDto;
import com.example.astraapi.entity.TestingEntity;
import com.example.astraapi.entity.TestingWithSpecializationEntity;
import com.example.astraapi.entity.projection.TestingInfoProjection;
import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.mapper.TestingMapper;
import com.example.astraapi.mapper.TestingTestMapper;
import com.example.astraapi.meta.TestingStatus;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.repository.TestRepository;
import com.example.astraapi.repository.TestingRepository;
import com.example.astraapi.repository.TestingTestRepository;
import com.example.astraapi.service.impl.TestingTestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestingTestServiceTest {
    @InjectMocks
    private TestingTestServiceImpl service;
    @Mock
    private TestingTestRepository testingTestRepository;
    @Mock
    private TestingTestMapper testingTestMapper;
    @Mock
    private TestingMapper testingMapper;
    @Mock
    private TestRepository testRepository;
    @Mock
    private TestingRepository testingRepository;

    @BeforeEach
    public void beforeEach() {
        setupTestingTestMapper();
        setupTestingMapper();
    }

    @Test
    void shouldThrowValidationExceptionWhenNotValidSpecialization() {
        when(testingTestRepository.hasValidSpecialization(any(), any())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.save(new RequestTestingTestDto(1L, 2L)));
        assertEquals("You cannot add this test to this testing", exception.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenNotActiveTest() {
        when(testingTestRepository.hasValidSpecialization(any(), any())).thenReturn(true);
        when(testRepository.existsByIdAndStatus(any(), any())).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> service.save(new RequestTestingTestDto(1L, 2L)));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.INVALID_STATUS, exception.getErrors().get(0).getType());
    }

    @Test
    void shouldThrowValidationExceptionWhenNotActiveTesting() {
        when(testingTestRepository.hasValidSpecialization(any(), any())).thenReturn(true);
        when(testRepository.existsByIdAndStatus(any(), any())).thenReturn(true);
        when(testingRepository.existsByIdAndStatus(any(), any())).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> service.save(new RequestTestingTestDto(1L, 2L)));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.INVALID_STATUS, exception.getErrors().get(0).getType());
    }

    @Test
    void shouldThrowValidationExceptionWhenAlreadyExistsOnSave() {
        when(testingTestRepository.hasValidSpecialization(any(), any())).thenReturn(true);
        when(testRepository.existsByIdAndStatus(any(), any())).thenReturn(true);
        when(testingRepository.existsByIdAndStatus(any(), any())).thenReturn(true);
        when(testingTestRepository.existsByTestingIdAndTestId(any(), any())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> service.save(new RequestTestingTestDto(1L, 2L)));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.CONFLICT, exception.getErrors().get(0).getType());
    }

    @Test
    void shouldSaveByTestingIdAndTestId() {
        when(testingTestRepository.hasValidSpecialization(any(), any())).thenReturn(true);
        when(testRepository.existsByIdAndStatus(any(), any())).thenReturn(true);
        when(testingRepository.existsByIdAndStatus(any(), any())).thenReturn(true);
        when(testingTestRepository.existsByTestingIdAndTestId(any(), any())).thenReturn(false);

        service.save(new RequestTestingTestDto(1L, 2L));

        verify(testingTestRepository, times(1)).save(any());
    }

    @Test
    void shouldSaveByTestingDetail() {
        when(testingTestRepository.hasValidSpecialization(any(), any())).thenReturn(true);
        when(testRepository.existsByIdAndStatus(any(), any())).thenReturn(true);
        when(testingRepository.existsByIdAndStatus(any(), any())).thenReturn(true);
        when(testingRepository.getByExamIdAndSpecializationId(any(), any())).thenReturn(null);
        when(testingTestRepository.existsByTestingIdAndTestId(any(), any())).thenReturn(false);

        service.save(new RequestTestingDetailTestDto(1L, 2L, 3L));

        verify(testingTestRepository, times(1)).save(any());
        verify(testingRepository, times(1)).save(any());
    }

    @Test
    void shouldDeleteById() {
        when(testingTestRepository.hasTestingStatus(any(), any())).thenReturn(true);

        service.delete(1L);

        verify(testingTestRepository, times(1)).deleteById(any());
    }

    @Test
    void shouldThrowExceptionWhenDeleteNotFromDraftTestingByTestingTestId() {
        when(testingTestRepository.hasTestingStatus(any(), any())).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> service.delete(1L));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.INVALID_STATUS, exception.getErrors().get(0).getType());
    }

    @Test
    void shouldDeleteByTestingTest() {
        when(testingRepository.existsByIdAndStatus(any(), any())).thenReturn(true);

        service.delete(new RequestTestingTestDto(1L, 2L));

        verify(testingTestRepository, times(1)).deleteByTestingIdAndTestId(any(), any());
    }

    @Test
    void shouldThrowExceptionWhenDeleteNotFromDraftTestingByTestingIdAndTestId() {
        when(testingRepository.existsByIdAndStatus(any(), any())).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> service.delete(new RequestTestingTestDto(1L, 2L)));
        assertEquals(1, exception.getErrors().size());
        assertEquals(ValidationErrorType.INVALID_STATUS, exception.getErrors().get(0).getType());
    }

    @Test
    void shouldReturnTestingsForTest() {
        when(testingTestRepository.getTestings(any())).thenReturn(List.of(
                new TestingInfoProjection(1L, null, TestingStatus.ACTIVE, null, null),
                new TestingInfoProjection(2L, null, TestingStatus.DRAFT, null, null),
                new TestingInfoProjection(3L, null, TestingStatus.ACTIVE, null, null)
        ));

        List<TestingDetailDto> items = service.getTestings(1L);

        assertEquals(3, items.size());
    }

    private void setupTestingTestMapper() {
        TestingTestMapper mapper = Mappers.getMapper(TestingTestMapper.class);

        lenient().when(testingTestMapper.toEntity(any())).thenAnswer(invocation -> mapper.toEntity(invocation.getArgument(0)));
    }

    private void setupTestingMapper() {
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
}
