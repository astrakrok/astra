package com.example.astraapi.dto.test;

import com.example.astraapi.dto.examination.ExaminationVariantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationTestDto {
    private Long id;
    private String question;
    private String questionSvg;
    private Long userAnswer;
    private List<ExaminationVariantDto> variants;
}
