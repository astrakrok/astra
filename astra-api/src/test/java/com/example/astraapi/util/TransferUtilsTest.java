package com.example.astraapi.util;

import com.example.astraapi.meta.ImportFileHeader;
import com.example.astraapi.model.importing.ImportSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransferUtilsTest {
    @ParameterizedTest
    @ValueSource(strings = {" # ", " №  ", "номер", "  Номер", "НоМеР"})
    void shouldReturnNumberImportFileHeader(String value) {
        ImportFileHeader header = TransferUtils.getHeader(value);

        assertEquals(ImportFileHeader.NUMBER, header);
    }

    @ParameterizedTest
    @ValueSource(strings = {"питання   ", " ПиТаннЯ"})
    void shouldReturnQuestionImportFileHeader(String value) {
        ImportFileHeader header = TransferUtils.getHeader(value);

        assertEquals(ImportFileHeader.QUESTION, header);
    }

    @ParameterizedTest
    @ValueSource(strings = {"КоментаР", "    коментар", "КоментаРі"})
    void shouldReturnCommentImportFileHeader(String value) {
        ImportFileHeader header = TransferUtils.getHeader(value);

        assertEquals(ImportFileHeader.COMMENT, header);
    }

    @ParameterizedTest
    @ValueSource(strings = {"предмет", " Предмети  ", "ПредметИ"})
    void shouldReturnSubjectImportFileHeader(String value) {
        ImportFileHeader header = TransferUtils.getHeader(value);

        assertEquals(ImportFileHeader.SUBJECT, header);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Варіант", "варіанти   ", " ВаріАнТи    "})
    void shouldReturnVariantImportFileHeader(String value) {
        ImportFileHeader header = TransferUtils.getHeader(value);

        assertEquals(ImportFileHeader.TITLE, header);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Пояснення", "  пояснення", " пояснення   "})
    void shouldReturnExplanationImportFileHeader(String value) {
        ImportFileHeader header = TransferUtils.getHeader(value);

        assertEquals(ImportFileHeader.EXPLANATION, header);
    }

    @ParameterizedTest
    @ValueSource(strings = {"+/-", " +/-   "})
    void shouldReturnCorrectnessImportFileHeader(String value) {
        ImportFileHeader header = TransferUtils.getHeader(value);

        assertEquals(ImportFileHeader.CORRECTNESS, header);
    }

    @Test
    void shouldReturnNullForNullHeader() {
        assertNull(TransferUtils.getHeader(null));
    }

    @Test
    void shouldReturnNullForUnrecognizedHeader() {
        assertNull(TransferUtils.getHeader("unrecognized"));
    }

    @Test
    void shouldReturnEmptyListForNullValue() {
        List<ImportSubject> list = TransferUtils.parseSubjects(null);

        assertEquals(0, list.size());
    }

    @Test
    void shouldParseSubjects() {
        List<ImportSubject> subjects = TransferUtils.parseSubjects(",   ,  subject 1 | specialization 1 | step 1, subject 2 | specialization 2, subject 3,   , , ");

        assertEquals(3, subjects.size());

        assertEquals("subject 1", subjects.get(0).getSubjectTitle());
        assertEquals("specialization 1", subjects.get(0).getSpecializationTitle());
        assertEquals("step 1", subjects.get(0).getStepTitle());

        assertEquals("subject 2", subjects.get(1).getSubjectTitle());
        assertEquals("specialization 2", subjects.get(1).getSpecializationTitle());
        assertNull(subjects.get(1).getStepTitle());

        assertEquals("subject 3", subjects.get(2).getSubjectTitle());
        assertNull(subjects.get(2).getSpecializationTitle());
        assertNull(subjects.get(2).getStepTitle());
    }

    @Test
    void shouldThrowExceptionWhenTryingToInstantiateClass() throws NoSuchMethodException {
        Constructor<TransferUtils> constructor = TransferUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(Exception.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }
}
