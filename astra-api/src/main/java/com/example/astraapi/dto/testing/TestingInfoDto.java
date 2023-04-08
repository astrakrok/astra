package com.example.astraapi.dto.testing;

import com.example.astraapi.dto.exam.ResponseExamDto;
import com.example.astraapi.dto.specialization.SpecializationDto;
import com.example.astraapi.meta.TestingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestingInfoDto {
    private Long id;
    private TestingStatus status;
    private Long testsCount;
    private ResponseExamDto exam;
    private SpecializationDto specialization;
}
