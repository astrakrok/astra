package com.example.astraapi.model.importing;

import com.example.astraapi.meta.ImportSource;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportResult {
    private ImportSource source;
    private String sourceTitle;
    private List<ImportTest> tests = new ArrayList<>();
    private Map<String, Object> details = new HashMap<>();
}
