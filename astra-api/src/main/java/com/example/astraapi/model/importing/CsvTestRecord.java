package com.example.astraapi.model.importing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CsvTestRecord {
    private String number;
    private String question;
    private String comment;
    private String subject;
    private String variant;
    private String explanation;
    private boolean correct;
}
