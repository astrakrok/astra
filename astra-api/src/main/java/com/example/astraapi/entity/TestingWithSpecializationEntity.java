package com.example.astraapi.entity;

import com.example.astraapi.entity.projection.StepSpecializationProjection;
import com.example.astraapi.meta.TestingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestingWithSpecializationEntity {
    private Long id;
    private Long examId;
    private TestingStatus status;
    private StepSpecializationProjection specialization;
}
