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
    return pageNumber * pageSize;
  }

  public void setPageNumber(Long pageNumber) {
    this.pageNumber = Math.max(0L, pageNumber);
  }

  public void setPageSize(Long pageSize) {
    this.pageSize = Math.max(0L, pageSize);
  }
}
