package com.example.astraapi.model.importing;

import com.example.astraapi.entity.projection.ImportSubjectProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportSubjectResult {
    private List<ImportSubjectProjection> notFoundSubjects = new ArrayList<>();
    private List<ImportSubjectProjection> validSubjects = new ArrayList<>();
    private List<Pair<String, List<ImportSubjectProjection>>> duplicateSubjects = new ArrayList<>();
}
