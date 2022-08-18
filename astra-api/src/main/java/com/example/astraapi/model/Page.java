package com.example.astraapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
  private List<T> items = new ArrayList<>();
  private long rows;
  private long pageSize;
  private long pagesCount;

  public long getPagesCount() {
    if (pageSize == 0) {
      return 0;
    }
    return (long) Math.ceil(rows * 1.0 / pageSize);
  }

  public void setPageSize(long pageSize) {
    this.pageSize = Math.max(0, pageSize);
  }
}
