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
      return 1l;
    }
    return pageNumber * pageSize;
  }

  public void setPageNumber(Long pageNumber) {
    if (pageNumber < 0) {
      this.pageNumber = 0l;
    }
    this.pageNumber = pageNumber;
  }

  public void setPageSize(Long pageSize) {
    if (pageSize < 0) {
      this.pageSize = 0l;
    }
    this.pageSize = pageSize;
  }
}
