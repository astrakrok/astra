package com.example.astraapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {
  @PositiveOrZero
  private long pageSize;
  @PositiveOrZero
  private long pageNumber;

  public Long getOffset() {
    return pageNumber * pageSize;
  }

  public void setPageNumber(long pageNumber) {
    this.pageNumber = Math.max(0L, pageNumber);
  }

  public void setPageSize(long pageSize) {
    this.pageSize = Math.max(0L, pageSize);
  }
}
