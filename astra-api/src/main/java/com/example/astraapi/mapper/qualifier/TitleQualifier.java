package com.example.astraapi.mapper.qualifier;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class TitleQualifier {
  public static final String TRIM = "trim";

  @Named(TRIM)
  public String getTrimmedValue(String value) {
    return value == null ? null : value.strip();
  }
}
