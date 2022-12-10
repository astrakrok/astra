package com.example.astraapi.util;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetUtilsTest {
  @Test
  void shouldReturnEmptySetOnEquivalentArguments() {
    Set<String> first = Set.of("value A", "value B");
    Set<String> second = Set.of("value A", "value B");
    Set<String> difference = SetUtils.getDifference(first, second);
    assertEquals(0, difference.size());
  }

  @Test
  void shouldReturnItemsFromFirstSetOnDistinctValues() {
    Set<String> first = Set.of("value A", "value B");
    Set<String> second = Set.of("value C", "value D", "value E");
    Set<String> difference = SetUtils.getDifference(first, second);
    assertEquals(2, difference.size());
    assertTrue(difference.contains("value A"));
    assertTrue(difference.contains("value B"));
  }

  @Test
  void shouldReturnEmptySetOnEmptyFirstSetAndNotEmptySecondSet() {
    Set<String> first = Set.of();
    Set<String> second = Set.of("value A", "value B");
    Set<String> difference = SetUtils.getDifference(first, second);
    assertEquals(0, difference.size());
  }

  @Test
  void shouldReturnItemsFromFirstSetOnEmptySecondSet() {
    Set<String> first = Set.of("value A", "value B", "value C");
    Set<String> second = Set.of();
    Set<String> difference = SetUtils.getDifference(first, second);
    assertEquals(3, difference.size());
    assertTrue(difference.contains("value A"));
    assertTrue(difference.contains("value B"));
    assertTrue(difference.contains("value C"));
  }

  @Test
  void shouldReturnItemsFromFirstSetWhichNotPresentInSecondSet() {
    Set<String> first = Set.of("value A", "value B", "value C", "value D");
    Set<String> second = Set.of("value C", "value D", "value E");
    Set<String> difference = SetUtils.getDifference(first, second);
    assertEquals(2, difference.size());
    assertTrue(difference.contains("value A"));
    assertTrue(difference.contains("value B"));
  }
}
