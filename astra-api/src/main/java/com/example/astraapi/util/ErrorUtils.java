package com.example.astraapi.util;

import com.example.astraapi.dto.ErrorDto;
import com.example.astraapi.dto.ErrorResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorUtils {
  public static ErrorResponseDto create(String message) {
    ErrorDto errorDto = new ErrorDto(message);
    return new ErrorResponseDto(errorDto);
  }
}
