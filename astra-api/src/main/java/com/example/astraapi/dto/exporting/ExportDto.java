package com.example.astraapi.dto.exporting;

import com.example.astraapi.meta.FileType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportDto {
    private Long specializationId;
    @NotNull
    private FileType fileType;
}
