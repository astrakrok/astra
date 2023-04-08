package com.example.astraapi.dto.examination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationAnswerDto {
    private Long id;
    private Long examinationId;
    @NotNull
    @Positive
    private Long testId;
    @Positive
    private Long variantId;
}
