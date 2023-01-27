package com.example.astraapi.entity;

import com.example.astraapi.entity.projection.TestFullDetailProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationAnswerEntity {
    private Long id;
    private Long examinationId;
    private Long testId;
    private Long variantId;
    private TestFullDetailProjection test;
}
