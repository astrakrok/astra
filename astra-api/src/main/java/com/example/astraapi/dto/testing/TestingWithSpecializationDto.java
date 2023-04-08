package com.example.astraapi.dto.testing;

import com.example.astraapi.dto.specialization.StepSpecializationDto;
import com.example.astraapi.meta.TestingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestingWithSpecializationDto {
    private Long id;
    private Long examId;
    private TestingStatus status;
    private StepSpecializationDto specialization;
}
