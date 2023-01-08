package com.example.astraapi.entity.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportSubjectProjection {
    private Long subjectId;
    private String subjectTitle;
    private Long specializationId;
    private String specializationTitle;
    private Long stepId;
    private String stepTitle;
    private String importSubjectTitle;
}
