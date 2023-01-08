package com.example.astraapi.dto.test.variant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestVariantDto {
    private Long id;
    private Long testId;
    private String title;
    private String titleSvg;
    private String explanation;
    private String explanationSvg;
    @JsonProperty("isCorrect")
    private boolean correct;
}
