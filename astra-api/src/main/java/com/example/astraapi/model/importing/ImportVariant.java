package com.example.astraapi.model.importing;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportVariant {
    private String title;
    private String explanation;
    private boolean correct;
}
