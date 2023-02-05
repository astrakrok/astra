package com.example.astraapi.service;

import com.example.astraapi.model.Change;
import com.example.astraapi.repository.TestSubjectRepository;
import com.example.astraapi.service.impl.TestSubjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestSubjectServiceTest {
    @InjectMocks
    private TestSubjectServiceImpl testSubjectService;
    @Mock
    private ChangeService changeService;
    @Mock
    private TestSubjectRepository testSubjectRepository;

    @Test
    void shouldSaveSubjectsIds() {
        Long[] id = new Long[1];

        doAnswer(invocation -> {
            Long testId = invocation.getArgument(0);
            id[0] = testId;
            return null;
        }).when(testSubjectRepository).save(any(), any());

        testSubjectService.save(5L, List.of(5L, 3L));

        assertEquals(5L, id[0]);
    }

    @Test
    void shouldNotCallSaveMethodOnEmptySubjectIds() {
        testSubjectService.save(5L, Collections.emptyList());

        verify(testSubjectRepository, never()).save(any(), any());
    }

    @Test
    void shouldNotAddAndNotRemoveSubjectsIds() {
        when(testSubjectRepository.getSubjectsIdsByTestId(any())).thenReturn(Set.of(1L, 2L, 3L));
        when(changeService.getChange(any(), any())).thenReturn(new Change<>(Collections.emptySet(), Collections.emptySet()));

        testSubjectService.update(5L, Set.of(5L, 6L));

        verify(testSubjectRepository, never()).save(any(), any());
        verify(testSubjectRepository, never()).delete(any(), any());
    }

    @Test
    void shouldAddAndRemoveSubjectsIds() {
        when(testSubjectRepository.getSubjectsIdsByTestId(any())).thenReturn(Set.of(1L, 2L, 3L));
        when(changeService.getChange(any(), any())).thenReturn(new Change<>(Set.of(1L, 2L), Set.of(5L, 6L)));

        testSubjectService.update(5L, Set.of(5L, 6L));
        
        verify(testSubjectRepository, times(1)).save(any(), any());
        verify(testSubjectRepository, times(1)).delete(any(), any());
    }
}
