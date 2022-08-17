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
  private int rows;
  private int pageSize;
  private int pagesCount;

  public int getPagesCount() {
    return (int) Math.ceil((float) rows / (float) pageSize);
  }
}
