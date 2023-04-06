package com.example.astraapi.dto.testing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestingDescriptionDto {
    private String trainingDescription;
    private String examinationDescription;
    private String adaptiveDescription;
}
