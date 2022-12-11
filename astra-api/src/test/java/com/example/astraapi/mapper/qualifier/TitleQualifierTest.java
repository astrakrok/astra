package com.example.astraapi.mapper.qualifier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class TitleQualifierTest {
  @InjectMocks
  private TitleQualifier titleQualifier;

  @Test
  void shouldReturnNullWhenNull() {
    assertNull(titleQualifier.getTrimmedValue(null));
  }

  @Test
  void shouldRemoveTrailingSpaces() {
    assertEquals("value", titleQualifier.getTrimmedValue("   value "));
  }

  @Test
  void shouldReturnPassedValue() {
    assertEquals("value", titleQualifier.getTrimmedValue("value"));
  }

  @Test
  void shouldReturnEmptyStringWhenEmptyString() {
    assertEquals("", titleQualifier.getTrimmedValue(""));
  }

  @Test
  void shouldReturnEmptyStringWhenStringWithWhitespaceCharacters() {
    assertEquals("", titleQualifier.getTrimmedValue("    \t   \n      "));
  }
}
