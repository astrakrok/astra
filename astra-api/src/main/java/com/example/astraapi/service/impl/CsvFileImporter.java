package com.example.astraapi.service.impl;

import com.example.astraapi.exception.ImportException;
import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.factory.CsvFactory;
import com.example.astraapi.meta.ImportFileHeader;
import com.example.astraapi.meta.ImportSource;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.importing.CsvTestRecord;
import com.example.astraapi.model.importing.ImportResult;
import com.example.astraapi.model.importing.ImportTest;
import com.example.astraapi.model.importing.ImportVariant;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.service.FileImporter;
import com.example.astraapi.util.TransferUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("csvFileImporter")
@RequiredArgsConstructor
public class CsvFileImporter implements FileImporter {
    private final CsvFactory csvFactory;

    @Override
    public ImportResult importTests(MultipartFile file) {
        try (CSVParser parser = createParser(file)) {
            Iterator<CSVRecord> iterator = parser.iterator();
            if (!iterator.hasNext()) {
                throw new ValidationException(new ValidationError(ValidationErrorType.EMPTY));
            }
            CSVRecord headerRecord = iterator.next();
            Map<ImportFileHeader, Integer> headers = getHeaders(headerRecord);
            List<ImportTest> tests = parser.stream()
                    .map(record -> parseTestRecord(record, headers))
                    .collect(Collectors.groupingBy(CsvTestRecord::getNumber))
                    .entrySet().stream()
                    .map(this::mapToImportTest)
                    .collect(Collectors.toList());
            return ImportResult.builder()
                    .source(ImportSource.CSV_FILE)
                    .sourceTitle(file.getOriginalFilename())
                    .tests(tests)
                    .details(new HashMap<>())
                    .build();
        } catch (NullPointerException | IOException exception) {
            throw new ImportException(ValidationErrorType.UNKNOWN, exception);
        }
    }

    private CsvTestRecord parseTestRecord(CSVRecord record, Map<ImportFileHeader, Integer> headers) {
        return CsvTestRecord.builder()
                .number(getRecordValue(record, headers.get(ImportFileHeader.NUMBER)))
                .question(getRecordValue(record, headers.get(ImportFileHeader.QUESTION)))
                .comment(getRecordValue(record, headers.get(ImportFileHeader.COMMENT)))
                .subject(getRecordValue(record, headers.get(ImportFileHeader.SUBJECT)))
                .variant(getRecordValue(record, headers.get(ImportFileHeader.TITLE)))
                .explanation(getRecordValue(record, headers.get(ImportFileHeader.EXPLANATION)))
                .correct("+".equals(getRecordValue(record, headers.get(ImportFileHeader.CORRECTNESS))))
                .build();
    }

    private String getRecordValue(CSVRecord record, Integer columnIndex) {
        return columnIndex == null ? null : StringUtils.strip(record.get(columnIndex));
    }

    private ImportTest mapToImportTest(Map.Entry<String, List<CsvTestRecord>> entry) {
        List<ImportVariant> variants = entry.getValue().stream()
                .map(this::toImportVariant)
                .collect(Collectors.toList());

        CsvTestRecord record = entry.getValue().get(0);
        return ImportTest.builder()
                .question(record.getQuestion())
                .comment(record.getComment())
                .subjects(TransferUtils.parseSubjects(record.getSubject()))
                .variants(variants)
                .build();
    }

    private ImportVariant toImportVariant(CsvTestRecord record) {
        return ImportVariant.builder()
                .title(record.getVariant())
                .explanation(record.getExplanation())
                .correct(record.isCorrect())
                .build();
    }

    private CSVParser createParser(MultipartFile file) throws IOException {
        CSVFormat format = CSVFormat.Builder
                .create(CSVFormat.DEFAULT)
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .build();
        return csvFactory.newParser(file, format);
    }

    private Map<ImportFileHeader, Integer> getHeaders(CSVRecord record) {
        Map<ImportFileHeader, Integer> headers = new HashMap<>();
        for (int i = 0; i < record.size(); i++) {
            String header = record.get(i);
            ImportFileHeader importHeader = TransferUtils.getHeader(header);
            if (importHeader != null) {
                headers.put(importHeader, i);
            }
        }
        return headers;
    }
}
