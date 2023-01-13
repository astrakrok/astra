package com.example.astraapi.model.exporting;

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
public class ExportTest {
    private String question;
    private String comment;
    private List<ExportVariant> variants = new ArrayList<>();
    private List<ExportSubject> subjects = new ArrayList<>();
}
