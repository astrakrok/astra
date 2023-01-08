package com.example.astraapi.entity;

import com.example.astraapi.meta.TestingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestingEntity {
    private Long id;
    private Long examId;
    private Long specializationId;
    private TestingStatus status;
    private ExamEntity exam;
    private SpecializationEntity specialization;
}
