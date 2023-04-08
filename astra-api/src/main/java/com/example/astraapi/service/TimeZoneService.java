package com.example.astraapi.service;

import java.time.LocalDateTime;

public interface TimeZoneService {
    LocalDateTime toUtc(LocalDateTime dateTime);
}
