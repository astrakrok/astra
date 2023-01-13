package com.example.astraapi.dto.exporting;

import com.example.astraapi.meta.DocumentType;
import com.example.astraapi.meta.ExcelType;
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
    private DocumentType documentType;
    private ExcelType excelType;
}
