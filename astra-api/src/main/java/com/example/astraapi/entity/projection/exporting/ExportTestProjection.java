package com.example.astraapi.entity.projection.exporting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportTestProjection {
    private String question;
    private String comment;
    private List<ExportTestVariantProjection> variants = new ArrayList<>();
    private List<ExportSubjectProjection> subjects = new ArrayList<>();
}
