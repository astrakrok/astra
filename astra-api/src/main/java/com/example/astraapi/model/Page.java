package com.example.astraapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
  private List<T> items = new ArrayList<>();
  private int rows;
  @Positive
  private int pageSize;
  @Positive
  private int pagesCount;

  public int getPagesCount() {
    if (pageSize == 0) {
      return 1;
    }
    return (int) Math.ceil((float) rows / (float) pageSize);
  }

  public void setPageSize(int pageSize) {
    if (pageSize < 1) {
      pagesCount = 1;
    }
  }
}
