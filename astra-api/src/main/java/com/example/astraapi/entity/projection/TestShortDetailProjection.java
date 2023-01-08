package com.example.astraapi.entity.projection;

import com.example.astraapi.entity.SpecializationEntity;
import com.example.astraapi.meta.TestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestShortDetailProjection {
    private Long id;
    private String question;
    private String comment;
    private TestStatus status;
    private List<SpecializationEntity> specializations;
}
