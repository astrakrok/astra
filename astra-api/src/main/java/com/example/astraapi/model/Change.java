package com.example.astraapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class Change<T> {
  private final Set<T> added;
  private final Set<T> removed;
}
