package com.example.astraapi.dto.testing;

import com.example.astraapi.dto.exam.ResponseExamDto;
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
public class TestingDetailDto {
    private Long id;
    private TestingStatus status;
    private ResponseExamDto exam;
    private StepSpecializationDto specialization;
}
