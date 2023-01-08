package com.example.astraapi.entity;

import com.example.astraapi.meta.TestStatus;
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
public class TestEntity {
    private Long id;
    private String question;
    private String comment;
    private String questionSvg;
    private String commentSvg;
    private TestStatus status;
    private List<TestVariantEntity> variants = new ArrayList<>();
}
