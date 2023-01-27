package com.example.astraapi.entity.projection;

import com.example.astraapi.entity.TestVariantEntity;
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
public class TestFullDetailProjection {
    private Long id;
    private String question;
    private String questionSvg;
    private String comment;
    private String commentSvg;
    private TestStatus status;
    private LocalDateTime createdDate;
    private List<TestingInfoProjection> testings = new ArrayList<>();
    private List<TestVariantEntity> variants = new ArrayList<>();
    private List<SubjectDetailProjection> subjects = new ArrayList<>();
    private Map<String, Object> importDetails = new HashMap<>();
}
