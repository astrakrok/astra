package com.example.astraapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {
  @Positive
  private Long pageSize;
  @Positive
  private Long pageNumber;

  public Long getOffset() {
    if (pageSize == 0) {
      return Long.valueOf(1);
    }
    return pageNumber * pageSize;
  }
}
