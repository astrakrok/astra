package com.example.astraapi.dto.specialization;

import com.example.astraapi.dto.step.StepDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StepSpecializationDto {
    private Long id;
    private String title;
    private StepDto step;
}
