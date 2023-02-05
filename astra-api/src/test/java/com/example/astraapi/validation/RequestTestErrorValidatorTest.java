package com.example.astraapi.validation;

import com.example.astraapi.dto.test.RequestTestDto;
import com.example.astraapi.dto.test.variant.TestVariantDto;
import com.example.astraapi.entity.projection.TestingInfoProjection;
import com.example.astraapi.meta.TestingStatus;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.repository.TestingRepository;
import com.example.astraapi.validation.impl.RequestTestErrorValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestTestErrorValidatorTest {
    @InjectMocks
    private RequestTestErrorValidator validator;
    @Mock
    private TestingRepository testingRepository;

    @Test
    void shouldReturnInvalidQuestionLengthError() {
        RequestTestDto test = new RequestTestDto(
                null,
                "question",
                "Example comment",
                null,
                null,
                List.of(
                        new TestVariantDto(
                                null,
                                null,
                                "Variant title",
                                null,
                                "Variant explanation",
                                null,
                                true
                        )
                ),
                Set.of(1L)
        );
        when(testingRepository.getRedundantTestings(any(), any())).thenReturn(Collections.emptyList());

        List<ValidationError> errors = validator.validate(test);

        assertEquals(1, errors.size());
        assertEquals(ValidationErrorType.INVALID_LENGTH, errors.get(0).getType());
        assertEquals("question", errors.get(0).getDetails().get("property"));
    }

    @Test
    void shouldReturnInvalidCommentLengthError() {
        RequestTestDto test = new RequestTestDto(
                null,
                "Example question",
                "comment",
                null,
                null,
                List.of(
                        new TestVariantDto(
                                null,
                                null,
                                "Variant title",
                                null,
                                "Variant explanation",
                                null,
                                true
                        )
                ),
                Set.of(1L)
        );
        when(testingRepository.getRedundantTestings(any(), any())).thenReturn(Collections.emptyList());

        List<ValidationError> errors = validator.validate(test);

        assertEquals(1, errors.size());
        assertEquals(ValidationErrorType.INVALID_LENGTH, errors.get(0).getType());
        assertEquals("comment", errors.get(0).getDetails().get("property"));
    }

    @Test
    void shouldReturnInvalidVariantTitleLengthError() {
        RequestTestDto test = new RequestTestDto(
                null,
                "Example question",
                "Example comment",
                null,
                null,
                List.of(
                        new TestVariantDto(
                                null,
                                null,
                                "",
                                null,
                                "Variant explanation",
                                null,
                                true
                        )
                ),
                Set.of(1L)
        );
        when(testingRepository.getRedundantTestings(any(), any())).thenReturn(Collections.emptyList());

        List<ValidationError> errors = validator.validate(test);

        assertEquals(1, errors.size());
        assertEquals(ValidationErrorType.INVALID_LENGTH, errors.get(0).getType());
        assertEquals("variants[0].title", errors.get(0).getDetails().get("property"));
    }

    @Test
    void shouldReturnInvalidVariantExplanationLengthError() {
        RequestTestDto test = new RequestTestDto(
                null,
                "Example question",
                "Example comment",
                null,
                null,
                List.of(
                        new TestVariantDto(
                                null,
                                null,
                                "Variant title",
                                null,
                                "example",
                                null,
                                true
                        )
                ),
                Set.of(1L)
        );
        when(testingRepository.getRedundantTestings(any(), any())).thenReturn(Collections.emptyList());

        List<ValidationError> errors = validator.validate(test);

        assertEquals(1, errors.size());
        assertEquals(ValidationErrorType.INVALID_LENGTH, errors.get(0).getType());
        assertEquals("variants[0].explanation", errors.get(0).getDetails().get("property"));
    }

    @Test
    void shouldReturnInvalidVariantsSizeError() {
        RequestTestDto test = new RequestTestDto(
                null,
                "Example question",
                "Example comment",
                null,
                null,
                Collections.emptyList(),
                Set.of(1L)
        );
        when(testingRepository.getRedundantTestings(any(), any())).thenReturn(Collections.emptyList());

        List<ValidationError> errors = validator.validate(test);

        assertEquals(2, errors.size());
        assertTrue(errors.stream().anyMatch(item -> item.getType() == ValidationErrorType.INVALID_SIZE));
        assertTrue(errors.stream().anyMatch(item -> item.getType() == ValidationErrorType.INVALID_CORRECTNESS));
    }

    @Test
    void shouldReturnInvalidSubjectsSizeError() {
        RequestTestDto test = new RequestTestDto(
                null,
                "Example question",
                "Example comment",
                null,
                null,
                List.of(
                        new TestVariantDto(
                                null,
                                null,
                                "Variant title",
                                null,
                                "Variant explanation",
                                null,
                                true
                        )
                ),
                Collections.emptySet()
        );
        when(testingRepository.getRedundantTestings(any(), any())).thenReturn(Collections.emptyList());

        List<ValidationError> errors = validator.validate(test);

        assertEquals(1, errors.size());
        assertEquals(ValidationErrorType.INVALID_SIZE, errors.get(0).getType());
        assertEquals("subjectIds", errors.get(0).getDetails().get("property"));
    }

    @Test
    void shouldReturnRedundantTestingsError() {
        RequestTestDto test = new RequestTestDto(
                null,
                "Example question",
                "Example comment",
                null,
                null,
                List.of(
                        new TestVariantDto(
                                null,
                                null,
                                "Variant title",
                                null,
                                "Variant explanation",
                                null,
                                true
                        )
                ),
                Set.of(1L)
        );
        when(testingRepository.getRedundantTestings(any(), any())).thenReturn(List.of(
                new TestingInfoProjection(null, null, TestingStatus.ACTIVE, null, null)
        ));

        List<ValidationError> errors = validator.validate(test);

        assertEquals(1, errors.size());
        assertEquals(ValidationErrorType.REDUNDANT_TESTINGS, errors.get(0).getType());
    }
}
