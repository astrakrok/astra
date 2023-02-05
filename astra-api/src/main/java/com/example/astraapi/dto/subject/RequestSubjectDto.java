package com.example.astraapi.dto.subject;

import com.example.astraapi.annotation.TrimmedLength;
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
public class RequestSubjectDto {
    @Positive
    private Long specializationId;
    @NotNull
    @TrimmedLength(min = 6, max = 255)
    private String title;
}
