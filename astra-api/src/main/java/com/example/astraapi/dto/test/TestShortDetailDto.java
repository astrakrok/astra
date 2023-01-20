package com.example.astraapi.dto.test;

import com.example.astraapi.dto.specialization.SpecializationDto;
import com.example.astraapi.meta.TestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestShortDetailDto {
    private Long id;
    private String question;
    private String comment;
    private TestStatus status;
    private LocalDateTime createdDate;
    private List<SpecializationDto> specializations;
}
