package com.example.astraapi.service.impl;

import com.example.astraapi.service.TimeZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TimeZoneServiceImpl implements TimeZoneService {
  @Override
  public LocalDateTime toUtc(LocalDateTime dateTime) {
    return dateTime
        .atZone(ZoneId.systemDefault())
        .withZoneSameInstant(ZoneOffset.UTC)
        .toLocalDateTime();
  }
}
