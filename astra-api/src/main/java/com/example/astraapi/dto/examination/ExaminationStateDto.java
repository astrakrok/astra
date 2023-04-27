package com.example.astraapi.dto.examination;

import com.example.astraapi.dto.test.ExaminationTestDto;
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
public class ExaminationStateDto {
    private Long id;
    private List<ExaminationTestDto> tests;
    private LocalDateTime finishedAt;
}
