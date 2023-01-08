package com.example.astraapi.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminSubjectFilterDto {
    private Long stepId;
    private Long specializationId;
    private String searchText;
}
