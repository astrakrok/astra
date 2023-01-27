package com.example.astraapi.dto.test;

import com.example.astraapi.dto.subject.ResponseSubjectDto;
import com.example.astraapi.dto.test.variant.TrainingVariantDto;
import com.example.astraapi.dto.testing.TestingDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdaptiveTestDto {
    private Long id;
    private String question;
    private String questionSvg;
    private String comment;
    private String commentSvg;
    private List<TestingDetailDto> testings = new ArrayList<>();
    private List<ResponseSubjectDto> subjects = new ArrayList<>();
    private List<TrainingVariantDto> variants = new ArrayList<>();
}
