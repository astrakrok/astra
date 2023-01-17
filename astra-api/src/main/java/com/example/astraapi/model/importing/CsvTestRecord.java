package com.example.astraapi.model.importing;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
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
