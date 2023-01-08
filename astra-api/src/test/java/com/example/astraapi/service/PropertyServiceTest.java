package com.example.astraapi.service;

import com.example.astraapi.dto.PropertyValueDto;
import com.example.astraapi.entity.PropertyEntity;
import com.example.astraapi.mapper.PropertyMapper;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import com.example.astraapi.meta.ConfigProperty;
import com.example.astraapi.repository.PropertyRepository;
import com.example.astraapi.service.impl.PropertyServiceImpl;
import com.example.astraapi.validation.ConfigPropertyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTest {
  @InjectMocks
  private PropertyServiceImpl propertyService;
  @Spy
  private PropertyMapper propertyMapper;
  @Mock
  private PropertyRepository propertyRepository;
  @Spy
  private Map<ConfigProperty, ConfigPropertyValidator> validators;

  @BeforeEach
  void beforeEach() {
    PropertyMapper mapper = Mappers.getMapper(PropertyMapper.class);
    TitleQualifier titleQualifier = new TitleQualifier();
    ReflectionTestUtils.setField(mapper, "titleQualifier", titleQualifier);

    Mockito.lenient().when(propertyMapper.toEntity(ArgumentMatchers.any(), ArgumentMatchers.any())).thenAnswer(invocation -> {
      String name = invocation.getArgument(0);
      String value = invocation.getArgument(1);

      return mapper.toEntity(name, value);
    });
  }

  @Test
  void shouldReturnPropertiesMap() {
    Mockito.when(propertyRepository.getPropertiesByNames(ArgumentMatchers.any())).thenReturn(List.of(
        new PropertyEntity(1L, "property1", "value1"),
        new PropertyEntity(2L, "property2", "value2"),
        new PropertyEntity(3L, "property3", "value3")
    ));

    Map<String, String> properties = propertyService.getProperties(Set.of());

    assertEquals(3, properties.keySet().size());
    assertEquals("value1", properties.get("property1"));
    assertEquals("value2", properties.get("property2"));
    assertEquals("value3", properties.get("property3"));
  }

  @Test
  void shouldReturnEmptyMap() {
    Mockito.when(propertyRepository.getPropertiesByNames(ArgumentMatchers.any())).thenReturn(List.of());

    Map<String, String> properties = propertyService.getProperties(Set.of());

    assertEquals(0, properties.keySet().size());
  }

  @Test
  void shouldReturnProperty() {
    Mockito.when(propertyRepository.getPropertyByName("name")).thenReturn(Optional.of(new PropertyEntity(1L, "name", "value")));

    Optional<PropertyEntity> property = propertyService.getProperty("name");

    assertFalse(property.isEmpty());
    assertEquals("name", property.get().getName());
    assertEquals("value", property.get().getValue());
  }

  @Test
  void shouldReturnEmptyProperty() {
    Mockito.when(propertyRepository.getPropertyByName("name")).thenReturn(Optional.empty());

    Optional<PropertyEntity> property = propertyService.getProperty("name");

    assertTrue(property.isEmpty());
  }

  @Test
  void shouldUpdateValidProperty() {
    PropertyEntity[] entity = new PropertyEntity[1];
    ConfigPropertyValidator validator = Mockito.mock(ConfigPropertyValidator.class);

    Mockito.when(validators.get(ConfigProperty.EXAMINATION_DESCRIPTION)).thenReturn(validator);

    Mockito.doAnswer(invocation -> {
      PropertyEntity propertyEntity = invocation.getArgument(0);
      entity[0] = propertyEntity;
      return null;
    }).when(propertyRepository).update(ArgumentMatchers.any());

    propertyService.update(ConfigProperty.EXAMINATION_DESCRIPTION, new PropertyValueDto("new value"));

    assertEquals(ConfigProperty.EXAMINATION_DESCRIPTION.getName(), entity[0].getName());
    assertEquals("new value", entity[0].getValue());
  }

  @Test
  void shouldThrowExceptionOnPropertyValidation() {
    ConfigPropertyValidator validator = Mockito.mock(ConfigPropertyValidator.class);
    Mockito.doThrow(new IllegalArgumentException("Invalid property value"))
        .when(validator).validate("new value");

    Mockito.when(validators.get(ConfigProperty.EXAMINATION_DESCRIPTION)).thenReturn(validator);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> propertyService.update(ConfigProperty.EXAMINATION_DESCRIPTION, new PropertyValueDto("new value")));
    assertEquals("Invalid property value", exception.getMessage());
  }

  @Test
  void shouldThrowNpeWhenNullValidator() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> propertyService.update(ConfigProperty.EXAMINATION_DESCRIPTION, new PropertyValueDto("new value")));
    assertEquals("Validator was not found", exception.getMessage());
  }
}
