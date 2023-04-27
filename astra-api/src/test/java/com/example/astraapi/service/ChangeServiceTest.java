package com.example.astraapi.service;

import com.example.astraapi.model.Change;
import com.example.astraapi.service.impl.ChangeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ChangeServiceTest {
    @InjectMocks
    private ChangeServiceImpl changeService;

    @Test
    void shouldReturnEmptyChangesOnEmptyValues() {
        Change<String> change = changeService.getChange(Set.of(), Set.of());

        assertEquals(0, change.getRemoved().size());
        assertEquals(0, change.getAdded().size());
    }

    @Test
    void shouldSetRemovedToOldValuesSetWhenSecondSetIsEmpty() {
        Change<String> change = changeService.getChange(Set.of("value A", "value B"), Set.of());

        assertEquals(2, change.getRemoved().size());
        assertEquals(0, change.getAdded().size());
        assertTrue(change.getRemoved().contains("value A"));
        assertTrue(change.getRemoved().contains("value B"));
    }

    @Test
    void shouldSetAddedToNedValuesSetWhenFirstSetIsEmpty() {
        Change<String> change = changeService.getChange(Set.of(), Set.of("value A", "value B"));

        assertEquals(0, change.getRemoved().size());
        assertEquals(2, change.getAdded().size());
        assertTrue(change.getAdded().contains("value A"));
        assertTrue(change.getAdded().contains("value B"));
    }

    @Test
    void shouldReturnEmptyChangesOnEquivalentSets() {
        Change<String> change = changeService.getChange(Set.of("value A", "value B"), Set.of("value A", "value B"));

        assertEquals(0, change.getRemoved().size());
        assertEquals(0, change.getAdded().size());
    }

    @Test
    void shouldReturnChanges() {
        Change<String> change = changeService.getChange(
                Set.of("value A", "value B", "value C", "value D"),
                Set.of("value C", "value D", "value E", "value F"));

        assertEquals(2, change.getRemoved().size());
        assertEquals(2, change.getAdded().size());
        assertTrue(change.getRemoved().contains("value A"));
        assertTrue(change.getRemoved().contains("value B"));
        assertTrue(change.getAdded().contains("value E"));
        assertTrue(change.getAdded().contains("value F"));
    }
}
