package com.example.astraapi.dto.filter;

import com.example.astraapi.meta.TestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminTestFilterDto {
    private String searchText;
    private TestStatus status;
    private Long importId;
}
