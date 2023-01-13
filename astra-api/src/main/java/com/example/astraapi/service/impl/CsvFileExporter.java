package com.example.astraapi.service.impl;

import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.entity.projection.exporting.ExportTestsPageResult;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.model.exporting.ExportSubject;
import com.example.astraapi.model.exporting.ExportTest;
import com.example.astraapi.model.exporting.ExportVariant;
import com.example.astraapi.service.FileExporter;
import com.example.astraapi.service.TestService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("csvFileExporter")
@RequiredArgsConstructor
public class CsvFileExporter implements FileExporter {
    private final TestService testService;

    @Override
    public byte[] exportTests(ExportDto exportDto) {
        CSVFormat format = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                .setHeader("#", "Питання", "Коментар", "Предмет", "Варіант", "Пояснення", "+/-")
                .setNullString("")
                .build();

        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(baos));
                CSVPrinter printer = new CSVPrinter(out, format)
        ) {
            int pageNumber = 0;
            ExportTestsPageResult exportResult = new ExportTestsPageResult(0, 0, true);
            boolean proceed = true;
            while (proceed) {
                Pageable pageable = new Pageable(100, pageNumber);
                exportResult = exportTests(printer, exportResult, exportDto, pageable);
                proceed = exportResult.isProceed();
                pageNumber++;
            }

            printer.flush();
            return baos.toByteArray();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private ExportTestsPageResult exportTests(CSVPrinter printer, ExportTestsPageResult result, ExportDto exportDto, Pageable pageable) throws IOException {
        Page<ExportTest> testsPage = testService.getTestsForExport(exportDto, pageable);
        List<ExportTest> tests = testsPage.getItems();
        for (int i = 0; i < tests.size(); i++) {
            ExportTest test = tests.get(i);
            for (int j = 0; j < test.getVariants().size(); j++) {
                ExportVariant variant = test.getVariants().get(j);
                List<String> values = Arrays.asList(
                        String.valueOf(result.getCount() + i + 1),
                        test.getQuestion(),
                        test.getComment(),
                        mapSubjectsToString(test.getSubjects()),
                        variant.getTitle(),
                        variant.getExplanation(),
                        variant.isCorrect() ? "+" : "-");
                printer.printRecord(values);
            }
        }
        return new ExportTestsPageResult(
                result.getLastRowNum() + tests.size(),
                result.getCount() + tests.size(),
                pageable.getPageNumber() + 1 < testsPage.getPagesCount());
    }

    private String mapSubjectsToString(List<ExportSubject> subjects) {
        return subjects.stream()
                .map(subject -> subject.getTitle() + "|" + subject.getSpecializationTitle() + "|" + subject.getStepTitle())
                .collect(Collectors.joining(","));
    }
}
