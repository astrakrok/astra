package com.example.astraapi.service.impl;

import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.entity.projection.exporting.ExportTestsPageResult;
import com.example.astraapi.meta.ExcelType;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.model.exporting.ExportSubject;
import com.example.astraapi.model.exporting.ExportTest;
import com.example.astraapi.model.exporting.ExportVariant;
import com.example.astraapi.service.FileExporter;
import com.example.astraapi.service.TestService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service("excelFileExporter")
@RequiredArgsConstructor
public class ExcelFileExporter implements FileExporter {
    private final TestService testService;

    @Override
    public byte[] exportTests(ExportDto exportDto) {
        try {
            try (Workbook workbook = WorkbookFactory.create(exportDto.getExcelType() == ExcelType.XLSX)) {
                Sheet sheet = workbook.createSheet("Exported");

                createHeaderRow(sheet);

                boolean proceed = true;
                int pageNumber = 0;
                ExportTestsPageResult exportResult = new ExportTestsPageResult(0, 0, true);
                while (proceed) {
                    Pageable pageable = new Pageable(100, pageNumber);
                    exportResult = exportTests(sheet, exportResult, exportDto, pageable);
                    proceed = exportResult.isProceed();
                    pageNumber++;
                }

                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                    workbook.write(outputStream);
                    return outputStream.toByteArray();
                }
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private ExportTestsPageResult exportTests(
            Sheet sheet,
            ExportTestsPageResult previousResult,
            ExportDto exportDto,
            Pageable pageable
    ) {
        Page<ExportTest> testsPage = testService.getTestsForExport(exportDto, pageable);
        List<ExportTest> tests = testsPage.getItems();
        int currentRow = previousResult.getLastRowNum() + 1;
        for (int i = 0; i < tests.size(); i++) {
            ExportTest test = tests.get(i);
            createTestRow(currentRow, sheet, previousResult.getCount() + i + 1, test);
            currentRow += test.getVariants().size();
        }
        return new ExportTestsPageResult(
                currentRow - 1,
                previousResult.getCount() + tests.size(),
                pageable.getPageNumber() + 1 < testsPage.getPagesCount());
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("#");
        headerRow.createCell(1).setCellValue("Питання");
        headerRow.createCell(2).setCellValue("Коментар");
        headerRow.createCell(3).setCellValue("Предмет");
        headerRow.createCell(4).setCellValue("Варіант");
        headerRow.createCell(5).setCellValue("Пояснення");
        headerRow.createCell(6).setCellValue("+/-");
    }

    private void createTestRow(int rowNum, Sheet sheet, int number, ExportTest test) {
        int lastRowNum = Math.max(rowNum + test.getVariants().size() - 1, rowNum);
        if (lastRowNum - rowNum + 1 >= 2) {
            sheet.addMergedRegion(new CellRangeAddress(rowNum, lastRowNum, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(rowNum, lastRowNum, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(rowNum, lastRowNum, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(rowNum, lastRowNum, 3, 3));
        }

        Row testRow = sheet.createRow(rowNum);
        testRow.createCell(0).setCellValue(number);
        testRow.createCell(1).setCellValue(test.getQuestion());
        testRow.createCell(2).setCellValue(test.getComment());
        testRow.createCell(3).setCellValue(mapSubjectsToString(test.getSubjects()));

        for (int i = 0; i < test.getVariants().size(); i++) {
            ExportVariant variant = test.getVariants().get(i);
            testRow.createCell(4).setCellValue(variant.getTitle());
            testRow.createCell(5).setCellValue(variant.getExplanation());
            testRow.createCell(6).setCellValue(variant.isCorrect() ? "+" : "-");
            if (i + 1 < test.getVariants().size()) {
                testRow = sheet.createRow(rowNum + i + 1);
            }
        }
    }

    private String mapSubjectsToString(List<ExportSubject> subjects) {
        return subjects.stream()
                .map(subject -> subject.getTitle() + "," + subject.getSpecializationTitle() + "," + subject.getStepTitle())
                .collect(Collectors.joining("|"));
    }
}
