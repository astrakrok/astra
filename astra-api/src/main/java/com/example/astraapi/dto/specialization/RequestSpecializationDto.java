package com.example.astraapi.dto.specialization;

import com.example.astraapi.annotation.TrimmedLength;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestSpecializationDto {
    @NotNull
    @TrimmedLength(min = 6, max = 255)
    private String title;
}
