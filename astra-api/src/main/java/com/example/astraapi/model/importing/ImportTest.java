package com.example.astraapi.model.importing;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportTest {
    private String question;
    private String comment;
    private List<ImportSubject> subjects = new ArrayList<>();
    private List<ImportVariant> variants = new ArrayList<>();
}
