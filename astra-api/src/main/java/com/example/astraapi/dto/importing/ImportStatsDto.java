package com.example.astraapi.dto.importing;

import com.example.astraapi.meta.ImportSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportStatsDto {
    private Long id;
    private String title;
    private ImportSource source;
    private String sourceTitle;
    private Map<String, Object> details = new HashMap<>();
    private LocalDateTime createdAt;
    private Long activeCount;
    private Long draftCount;
}
