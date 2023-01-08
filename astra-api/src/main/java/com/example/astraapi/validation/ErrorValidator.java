package com.example.astraapi.validation;

import com.example.astraapi.model.validation.ValidationError;

import java.util.List;

public interface ErrorValidator<T> {
    List<ValidationError> validate(T model);
}
