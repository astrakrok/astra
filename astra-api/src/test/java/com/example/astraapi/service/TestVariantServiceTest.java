package com.example.astraapi.service;

import com.example.astraapi.dto.test.variant.TestVariantDto;
import com.example.astraapi.mapper.TestVariantMapper;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import com.example.astraapi.model.Change;
import com.example.astraapi.repository.TestVariantRepository;
import com.example.astraapi.service.impl.TestVariantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestVariantServiceTest {
    @InjectMocks
    private TestVariantServiceImpl testVariantService;
    @Mock
    private ChangeService changeService;
    @Mock
    private TestVariantRepository variantRepository;
    @Mock
    private TestVariantMapper variantMapper;

    @BeforeEach
    public void beforeEach() {
        TestVariantMapper mapper = Mappers.getMapper(TestVariantMapper.class);
        ReflectionTestUtils.setField(mapper, "titleQualifier", new TitleQualifier());

        lenient().when(variantMapper.toEntity(any())).thenAnswer(invocation -> mapper.toEntity(invocation.getArgument(0)));
        lenient().when(variantMapper.toTrainingDto(any())).thenAnswer(invocation -> mapper.toTrainingDto(invocation.getArgument(0)));
        lenient().when(variantMapper.toExaminationDto(any())).thenAnswer(invocation -> mapper.toExaminationDto(invocation.getArgument(0)));
    }

    @Test
    void shouldSaveVariants() {
        testVariantService.save(5L, List.of(new TestVariantDto(null, 5L, "title", null, "explanation", null, true)));

        verify(variantRepository, times(1)).save(any(), any());
    }

    @Test
    void shouldNotCallSavingMethod() {
        testVariantService.save(5L, Collections.emptyList());

        verify(variantRepository, never()).save(any(), any());
    }

    @Test
    void shouldUpdateVariants() {
        when(variantRepository.getVariantsIdsByTestId(eq(5L))).thenReturn(Set.of(3L, 7L, 11L));
        when(changeService.getChange(any(), any())).thenReturn(new Change<>(Collections.emptySet(), Set.of(3L, 11L)));

        testVariantService.update(5L, List.of(
                new TestVariantDto(7L, 5L, "title", null, "explanation", null, true),
                new TestVariantDto(null, 5L, "title", null, "explanation", null, false)));

        verify(variantRepository, times(1)).delete(any(), any());
        verify(variantRepository, times(1)).save(any(), any());
        verify(variantRepository, times(1)).update(any(), any());
    }
}
