package com.example.astraapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {
  private Long pageSize;
  private Long pageNumber;

  public Long getOffset() {
    return pageNumber * pageSize;
  }
}
