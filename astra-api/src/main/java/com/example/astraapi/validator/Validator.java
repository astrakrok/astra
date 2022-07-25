package com.example.astraapi.validator;

public interface Validator<T> {
  void validate(T model);
}
