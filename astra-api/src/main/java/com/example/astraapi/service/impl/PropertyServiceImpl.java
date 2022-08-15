package com.example.astraapi.service.impl;

import com.example.astraapi.dto.PropertyValueDto;
import com.example.astraapi.entity.PropertyEntity;
import com.example.astraapi.mapper.PropertyMapper;
import com.example.astraapi.meta.ConfigProperty;
import com.example.astraapi.repository.PropertyRepository;
import com.example.astraapi.service.PropertyService;
import com.example.astraapi.validator.ConfigPropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
  private final PropertyMapper propertyMapper;
  private final PropertyRepository propertyRepository;
  private final Map<ConfigProperty, ConfigPropertyValidator> validators;

  @Override
  public Map<String, String> getProperties(Set<String> names) {
    List<PropertyEntity> properties = propertyRepository.getPropertiesByNames(names);
    Map<String, String> namesToValues = new HashMap<>();
    for (PropertyEntity property : properties) {
      namesToValues.put(property.getName(), property.getValue());
    }
    return namesToValues;
  }

  @Override
  public Optional<PropertyEntity> getProperty(String name) {
    return propertyRepository.getPropertyByName(name);
  }

  @Override
  public void update(ConfigProperty property, PropertyValueDto valueDto) {
    validatePropertyValue(property, valueDto.getValue());
    PropertyEntity propertyEntity = propertyMapper.toEntity(property.getName(), valueDto.getValue());
    propertyRepository.update(propertyEntity);
  }

  private void validatePropertyValue(ConfigProperty property, String value) {
    ConfigPropertyValidator validator = validators.get(property);
    if (validator == null) {
      throw new NullPointerException("Validator was not found");
    }
    validator.validate(value);
  }
}
