package com.example.astraapi.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
  SOMETHING_WENT_WRONG("Something went wrong. Try again later");

  private final String message;
}
