package com.example.astraapi.model.exporting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportVariant {
    private String title;
    private String explanation;
    private boolean correct;
}
