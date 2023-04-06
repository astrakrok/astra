package com.example.astraapi.validation;

public interface Validator<T> {
    void validate(T model);
}
