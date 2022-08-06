package com.example.astraapi.service;

import com.example.astraapi.model.Change;

import java.util.Set;

public interface ChangeService {
  <T> Change<T> getChange(Set<T> oldValues, Set<T> newValues);
}
