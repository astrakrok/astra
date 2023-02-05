package com.example.astraapi.model.importing;

import com.example.astraapi.meta.ImportSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class ImportResult {
    private ImportSource source;
    private String sourceTitle;
    private List<ImportTest> tests;
    private Map<String, Object> details;
}
