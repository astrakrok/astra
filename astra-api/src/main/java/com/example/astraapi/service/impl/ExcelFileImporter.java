package com.example.astraapi.service.impl;

import com.example.astraapi.exception.ImportException;
import com.example.astraapi.meta.ImportFileHeader;
import com.example.astraapi.meta.ImportSource;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.importing.ImportResult;
import com.example.astraapi.model.importing.ImportTest;
import com.example.astraapi.model.importing.ImportVariant;
import com.example.astraapi.service.FileImporter;
import com.example.astraapi.util.FileUtils;
import com.example.astraapi.util.TransferUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service("excelFileImporter")
@RequiredArgsConstructor
public class ExcelFileImporter implements FileImporter {
    @Override
    public ImportResult importTests(MultipartFile file) {
        try (Workbook workbook = WorkbookFactory.create(FileUtils.getInputStream(file))) {
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, String> ranges = getRanges(sheet);
            Map<ImportFileHeader, Integer> headers = getHeaders(sheet.getRow(0));
            List<ImportTest> tests = parseTests(sheet, ranges, headers);
            return ImportResult.builder()
                    .source(ImportSource.EXCEL_FILE)
                    .sourceTitle(file.getOriginalFilename())
                    .tests(tests)
                    .details(new HashMap<>())
                    .build();
        } catch (IOException exception) {
            throw new ImportException(ValidationErrorType.UNKNOWN, exception);
        }
    }

    private List<ImportTest> parseTests(Sheet sheet, Map<String, String> ranges, Map<ImportFileHeader, Integer> headers) {
        int lastRowNum = sheet.getLastRowNum();
        int i = 1;
        List<ImportTest> tests = new ArrayList<>();
        while (i <= lastRowNum) {
            Row startRow = sheet.getRow(i);
            Integer columnIndex = headers.get(ImportFileHeader.QUESTION);
            String startAddress = new CellReference(i, columnIndex).formatAsString();
            String endAddress = ranges.get(startAddress);
            int startRowNum = startRow.getRowNum();
            int endRowNum = new CellReference(endAddress == null ? startAddress : endAddress).getRow() + 1;
            ImportTest importTest = parseTest(sheet, headers, startRowNum, endRowNum);
            if (importTest != null) {
                tests.add(importTest);
            }
            i += endRowNum - startRowNum;
        }
        return tests;
    }

    private ImportTest parseTest(Sheet sheet, Map<ImportFileHeader, Integer> headers, int startRowNum, int endRowNum) {
        Row generalInfoRow = sheet.getRow(startRowNum);
        String question = getStringValue(generalInfoRow, headers.get(ImportFileHeader.QUESTION));
        String comment = getStringValue(generalInfoRow, headers.get(ImportFileHeader.COMMENT));
        String subjects = getStringValue(generalInfoRow, headers.get(ImportFileHeader.SUBJECT));
        List<ImportVariant> variants = new ArrayList<>();
        for (int i = startRowNum; i < endRowNum; i++) {
            Row row = sheet.getRow(i);
            String title = getStringValue(row, headers.get(ImportFileHeader.TITLE));
            String explanation = getStringValue(row, headers.get(ImportFileHeader.EXPLANATION));
            boolean correct = "+".equals(getStringValue(row, headers.get(ImportFileHeader.CORRECTNESS)));
            if (StringUtils.isAllBlank(question, comment, subjects, title, explanation)) {
                continue;
            }
            ImportVariant variant = new ImportVariant(title, explanation, correct);
            variants.add(variant);
        }
        if (StringUtils.isAllBlank(question, comment, subjects) && variants.isEmpty()) {
            return null;
        }
        return ImportTest.builder()
                .question(question)
                .comment(comment)
                .subjects(TransferUtils.parseSubjects(subjects))
                .variants(variants)
                .build();
    }

    private String getStringValue(Row row, Integer columnIndex) {
        if (columnIndex == null) {
            return null;
        }
        Cell cell = row.getCell(columnIndex);
        return cell == null ? null : cell.getStringCellValue();
    }

    private Map<String, String> getRanges(Sheet sheet) {
        return sheet.getMergedRegions().stream()
                .map(CellRangeAddress::formatAsString)
                .map(range -> range.split(":"))
                .map(values -> new AbstractMap.SimpleEntry<>(values[0], values[1]))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    private Map<ImportFileHeader, Integer> getHeaders(Row row) {
        short lastCellNum = row.getLastCellNum();
        Map<ImportFileHeader, Integer> headers = new HashMap<>();
        for (int i = 0; i < lastCellNum; i++) {
            String header = row.getCell(i).getStringCellValue();
            ImportFileHeader importHeader = TransferUtils.getHeader(header);
            if (importHeader != null) {
                headers.put(importHeader, i);
            }
        }
        return headers;
    }
}
