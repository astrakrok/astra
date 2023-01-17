package com.example.astraapi.util;

import com.example.astraapi.meta.ImportFileHeader;
import com.example.astraapi.model.importing.ImportSubject;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TransferUtils {
    private static final String[] NUMBERS_HEADERS = new String[]{"#", "№", "номер"};
    private static final String[] QUESTIONS_HEADERS = new String[]{"питання"};
    private static final String[] COMMENTS_HEADERS = new String[]{"коментар", "коментарі"};
    private static final String[] SUBJECTS_HEADERS = new String[]{"предмет", "предмети"};
    private static final String[] VARIANTS_HEADERS = new String[]{"варіант", "варіанти"};
    private static final String[] EXPLANATIONS_HEADERS = new String[]{"пояснення"};
    private static final String[] CORRECTNESS_HEADERS = new String[]{"правильність", "+/-"};

    public static ImportFileHeader getHeader(String value) {
        String strippedValue = StringUtils.strip(value);
        if (StringUtils.equalsAnyIgnoreCase(strippedValue, QUESTIONS_HEADERS)) {
            return ImportFileHeader.QUESTION;
        } else if (StringUtils.equalsAnyIgnoreCase(strippedValue, COMMENTS_HEADERS)) {
            return ImportFileHeader.COMMENT;
        } else if (StringUtils.equalsAnyIgnoreCase(strippedValue, SUBJECTS_HEADERS)) {
            return ImportFileHeader.SUBJECT;
        } else if (StringUtils.equalsAnyIgnoreCase(strippedValue, VARIANTS_HEADERS)) {
            return ImportFileHeader.TITLE;
        } else if (StringUtils.equalsAnyIgnoreCase(strippedValue, EXPLANATIONS_HEADERS)) {
            return ImportFileHeader.EXPLANATION;
        } else if (StringUtils.equalsAnyIgnoreCase(strippedValue, CORRECTNESS_HEADERS)) {
            return ImportFileHeader.CORRECTNESS;
        } else if (StringUtils.equalsAnyIgnoreCase(strippedValue, NUMBERS_HEADERS)) {
            return ImportFileHeader.NUMBER;
        }
        return null;
    }

    public static List<ImportSubject> parseSubjects(String subjects) {
        return subjects == null ? Collections.emptyList() : Arrays.stream(subjects.split(","))
                .map(String::strip)
                .filter(StringUtils::isNotBlank)
                .map(TransferUtils::toImportSubject)
                .collect(Collectors.toList());
    }

    private static ImportSubject toImportSubject(String value) {
        String[] parts = value.split("\\|");
        return new ImportSubject(
                StringUtils.strip(parts[0]),
                parts.length >= 2 ? StringUtils.strip(parts[1]) : null,
                parts.length >= 3 ? StringUtils.strip(parts[2]) : null
        );
    }
}
