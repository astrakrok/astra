package com.example.astraapi.model.importing;

import com.example.astraapi.meta.ImportSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportResult {
    private ImportSource source;
    private String sourceTitle;
    private List<ImportTest> tests;
}
