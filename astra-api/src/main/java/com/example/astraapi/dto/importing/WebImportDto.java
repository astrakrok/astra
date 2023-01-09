package com.example.astraapi.dto.importing;

import com.example.astraapi.annotation.TrimmedLength;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebImportDto {
    @TrimmedLength(min = 4)
    private String title;
    @TrimmedLength(min = 10)
    private String url;
}
