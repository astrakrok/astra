package com.example.astraapi.service;

import com.example.astraapi.service.impl.TimeZoneServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class TimeZoneServiceTest {
  @InjectMocks
  private TimeZoneServiceImpl timeZoneService;

  @Test
  void shouldReturnNullOnNullValue() {
    assertNull(timeZoneService.toUtc(null));
  }

  @Test
  void shouldConvertToUtcTime() {
    Instant now = Instant.now();
    LocalDateTime convertedValue = timeZoneService.toUtc(LocalDateTime.ofInstant(now, ZoneId.systemDefault()));

    LocalDateTime utcTime = ZonedDateTime.ofInstant(now, ZoneOffset.UTC).toLocalDateTime();

    assertEquals(utcTime.getYear(), convertedValue.getYear());
    assertEquals(utcTime.getMonth(), convertedValue.getMonth());
    assertEquals(utcTime.getDayOfMonth(), convertedValue.getDayOfMonth());
    assertEquals(utcTime.getHour(), convertedValue.getHour());
    assertEquals(utcTime.getMinute(), convertedValue.getMinute());
    assertEquals(utcTime.getSecond(), convertedValue.getSecond());
  }
}
