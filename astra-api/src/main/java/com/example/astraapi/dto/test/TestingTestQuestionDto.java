package com.example.astraapi.dto.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestingTestQuestionDto {
    private Long id;
    private Long testId;
    private String testQuestion;
}
