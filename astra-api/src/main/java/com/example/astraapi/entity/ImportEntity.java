package com.example.astraapi.entity;

import com.example.astraapi.meta.ImportSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportEntity {
    private Long id;
    private String title;
    private ImportSource source;
    private String sourceTitle;
    private Map<String, Object> details;
    private LocalDateTime createdAt;
}
