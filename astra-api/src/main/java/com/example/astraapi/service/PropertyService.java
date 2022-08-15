package com.example.astraapi.service;

import com.example.astraapi.dto.PropertyValueDto;
import com.example.astraapi.entity.PropertyEntity;
import com.example.astraapi.meta.ConfigProperty;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface PropertyService {
  Map<String, String> getProperties(Set<String> names);

  Optional<PropertyEntity> getProperty(String name);

  void update(ConfigProperty property, PropertyValueDto valueDto);
}
