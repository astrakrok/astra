package com.example.astraapi.model.importing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ImportTest {
    private String question;
    private String comment;
    private List<ImportSubject> subjects;
    private List<ImportVariant> variants;
}
