package com.example.astraapi.dto.test;

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
public class RequestTestingDetailTestDto {
    @NotNull
    @Positive
    private Long examId;
    @NotNull
    @Positive
    private Long specializationId;
    @NotNull
    @Positive
    private Long testId;
}
