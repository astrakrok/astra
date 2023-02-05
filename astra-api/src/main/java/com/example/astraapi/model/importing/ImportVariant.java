package com.example.astraapi.model.importing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ImportVariant {
    private String title;
    private String explanation;
    private boolean correct;
}
