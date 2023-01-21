package com.example.astraapi.dto.test;

import com.example.astraapi.dto.test.variant.TestVariantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTestDto {
    private Long id;
    private String question;
    private String comment;
    private String questionSvg;
    private String commentSvg;
    private List<TestVariantDto> variants = new ArrayList<>();
    private Set<Long> subjectIds = new HashSet<>();
}
