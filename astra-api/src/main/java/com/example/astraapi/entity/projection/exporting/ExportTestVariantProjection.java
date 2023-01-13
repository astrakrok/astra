package com.example.astraapi.entity.projection.exporting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportTestVariantProjection {
    private String title;
    private String explanation;
    private boolean correct;
}
