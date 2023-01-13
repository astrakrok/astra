package com.example.astraapi.entity.projection.exporting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportTestsPageResult {
    private int lastRowNum;
    private int count;
    private boolean proceed;
}
