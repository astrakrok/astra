package com.example.astraapi.dto.test;

import com.example.astraapi.dto.subject.ResponseSubjectDto;
import com.example.astraapi.dto.test.variant.TestVariantDto;
import com.example.astraapi.dto.testing.TestingDetailDto;
import com.example.astraapi.meta.TestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestFullDetailDto {
    private Long id;
    private String question;
    private String questionSvg;
    private String comment;
    private String commentSvg;
    private TestStatus status;
    private LocalDateTime createdDate;
    private List<TestingDetailDto> testings = new ArrayList<>();
    private List<TestVariantDto> variants = new ArrayList<>();
    private List<ResponseSubjectDto> subjects = new ArrayList<>();
    private Map<String, Object> importDetails = new HashMap<>();
}
