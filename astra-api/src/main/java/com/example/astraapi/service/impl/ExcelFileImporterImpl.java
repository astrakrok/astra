package com.example.astraapi.service.impl;

import com.example.astraapi.exception.ImportException;
import com.example.astraapi.meta.ImportFileHeader;
import com.example.astraapi.meta.ImportSource;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.importing.ImportResult;
import com.example.astraapi.model.importing.ImportSubject;
import com.example.astraapi.model.importing.ImportTest;
import com.example.astraapi.model.importing.ImportVariant;
import com.example.astraapi.service.FileImporter;
import com.example.astraapi.util.FileUtils;
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
public class ExcelFileImporterImpl implements FileImporter {
    private static final String[] QUESTIONS_HEADERS = new String[]{"питання"};
    private static final String[] COMMENTS_HEADERS = new String[]{"коментар", "коментарі"};
    private static final String[] SUBJECTS_HEADERS = new String[]{"предмет", "предмети"};
    private static final String[] VARIANTS_HEADERS = new String[]{"варіант", "варіанти"};
    private static final String[] EXPLANATIONS_HEADERS = new String[]{"пояснення"};
    private static final String[] CORRECTNESS_HEADERS = new String[]{"правильність", "+/-"};

    @Override
    public ImportResult importTests(MultipartFile file) {
        try (Workbook workbook = createWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, String> ranges = getRanges(sheet);
            Map<ImportFileHeader, Integer> headers = getHeaders(sheet.getRow(0));
            List<ImportTest> tests = parseTests(sheet, ranges, headers);
            return new ImportResult(ImportSource.EXCEL_FILE, file.getOriginalFilename(), tests);
        } catch (IOException exception) {
            throw new ImportException(ValidationErrorType.UNKNOWN, exception);
        }
    }

    private List<ImportTest> parseTests(Sheet sheet, Map<String, String> ranges, Map<ImportFileHeader, Integer> headers) {
        int lastRowNum = sheet.getLastRowNum();
        int i = 1;
        List<ImportTest> tests = new ArrayList<>();
        while (i < lastRowNum) {
            Row startRow = sheet.getRow(i);
            Cell cell = startRow.getCell(headers.get(ImportFileHeader.QUESTION));
            String startAddress = cell.getAddress().formatAsString();
            String endAddress = ranges.get(startAddress);
            if (endAddress == null) {
                i++;
                continue;
            }
            int startRowNum = startRow.getRowNum();
            int endRowNum = new CellReference(endAddress).getRow() + 1;
            ImportTest importTest = parseTest(sheet, headers, startRowNum, endRowNum);
            tests.add(importTest);
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
            String title = getStringValue(generalInfoRow, headers.get(ImportFileHeader.TITLE));
            String explanation = getStringValue(generalInfoRow, headers.get(ImportFileHeader.EXPLANATION));
            boolean correct = "+".equals(row.getCell(headers.get(ImportFileHeader.CORRECTNESS)).getStringCellValue());
            ImportVariant variant = new ImportVariant(title, explanation, correct);
            variants.add(variant);
        }
        return ImportTest.builder()
                .question(question)
                .comment(comment)
                .subjects(parseSubjects(subjects))
                .variants(variants)
                .build();
    }

    private String getStringValue(Row row, Integer column) {
        return column == null ? null : row.getCell(column).getStringCellValue();
    }

    private List<ImportSubject> parseSubjects(String subjects) {
        return subjects == null ? Collections.emptyList() : Arrays.stream(subjects.split(","))
                .map(String::strip)
                .filter(StringUtils::isNotBlank)
                .map(this::toImportSubject)
                .collect(Collectors.toList());
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
            if (StringUtils.equalsAnyIgnoreCase(header, QUESTIONS_HEADERS)) {
                headers.put(ImportFileHeader.QUESTION, i);
            } else if (StringUtils.equalsAnyIgnoreCase(header, COMMENTS_HEADERS)) {
                headers.put(ImportFileHeader.COMMENT, i);
            } else if (StringUtils.equalsAnyIgnoreCase(header, SUBJECTS_HEADERS)) {
                headers.put(ImportFileHeader.SUBJECT, i);
            } else if (StringUtils.equalsAnyIgnoreCase(header, VARIANTS_HEADERS)) {
                headers.put(ImportFileHeader.TITLE, i);
            } else if (StringUtils.equalsAnyIgnoreCase(header, EXPLANATIONS_HEADERS)) {
                headers.put(ImportFileHeader.EXPLANATION, i);
            } else if (StringUtils.equalsAnyIgnoreCase(header, CORRECTNESS_HEADERS)) {
                headers.put(ImportFileHeader.CORRECTNESS, i);
            }
        }
        return headers;
    }

    private ImportSubject toImportSubject(String value) {
        String[] parts = value.split("\\|");
        return new ImportSubject(
                StringUtils.strip(parts[0]),
                parts.length >= 2 ? StringUtils.strip(parts[1]) : null,
                parts.length >= 3 ? StringUtils.strip(parts[2]) : null
        );
    }

    private Workbook createWorkbook(MultipartFile file) {
        try {
            return WorkbookFactory.create(FileUtils.getInputStream(file));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
