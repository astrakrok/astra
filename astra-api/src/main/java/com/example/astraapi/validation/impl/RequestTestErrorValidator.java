package com.example.astraapi.validation.impl;

import com.example.astraapi.dto.test.RequestTestDto;
import com.example.astraapi.dto.test.variant.TestVariantDto;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.validation.ErrorValidator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RequestTestErrorValidator implements ErrorValidator<RequestTestDto> {
    @Override
    public List<ValidationError> validate(RequestTestDto model) {
        return Stream.concat(
                        Stream.of(
                                validateLength("question", model.getQuestion(), 10, null),
                                validateLength("comment", model.getComment(), 10, null),
                                validateSize("variants", model.getVariants(), 1, null),
                                validateSize("subjectIds", model.getSubjectIds(), 1, null),
                                validateCorrectness(model.getVariants())),
                        getVariantsErrorsStream(model.getVariants()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Stream<ValidationError> getVariantsErrorsStream(List<TestVariantDto> variants) {
        return IntStream.range(0, variants.size())
                .mapToObj(index -> Pair.of(index, variants.get(index)))
                .flatMap(pair -> Stream.of(
                        validateLength("variants[" + pair.getLeft() + "].title", pair.getRight().getTitle(), 1, null),
                        validateLength("variants[" + pair.getLeft() + "].explanation", pair.getRight().getExplanation(), 10, null)
                ));
    }

    private ValidationError validateLength(String property, String value, Integer min, Integer max) {
        int length = StringUtils.length(value == null ? null : value.strip());
        Map<String, Object> details = validateBounds(property, length, min, max);
        return details.isEmpty() ? null : new ValidationError(
                ValidationErrorType.INVALID_LENGTH,
                details);
    }

    private ValidationError validateSize(String property, Collection<?> items, Integer min, Integer max) {
        int size = items.size();
        Map<String, Object> details = validateBounds(property, size, min, max);
        return details.isEmpty() ? null : new ValidationError(
                ValidationErrorType.INVALID_SIZE,
                details);
    }

    private Map<String, Object> validateBounds(String property, int value, Integer min, Integer max) {
        if ((min == null || value >= min) && (max == null || value <= max)) {
            return new HashMap<>();
        }
        HashMap<String, Object> details = new HashMap<>();
        details.put("property", property);
        details.put("min", min);
        details.put("max", max);
        return details;
    }

    private ValidationError validateCorrectness(List<TestVariantDto> variants) {
        long correctCount = variants.stream()
                .filter(TestVariantDto::isCorrect)
                .count();
        return correctCount == 1 ? null : new ValidationError(
                ValidationErrorType.INVALID_CORRECTNESS,
                Collections.emptyMap()
        );
    }
}
