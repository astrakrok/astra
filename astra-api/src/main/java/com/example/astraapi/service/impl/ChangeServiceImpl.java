package com.example.astraapi.service.impl;

import com.example.astraapi.model.Change;
import com.example.astraapi.service.ChangeService;
import com.example.astraapi.util.SetUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChangeServiceImpl implements ChangeService {

  @Override
  public <T> Change<T> getChange(Set<T> oldValues, Set<T> newValues) {
    return new Change<>(
        SetUtils.getDifference(newValues, oldValues),
        SetUtils.getDifference(oldValues, newValues)
    );
  }
}
