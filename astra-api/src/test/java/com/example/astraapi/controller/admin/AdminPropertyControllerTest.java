package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.PropertyValueDto;
import com.example.astraapi.meta.ConfigProperty;
import com.example.astraapi.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anySet;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminPropertyControllerTest {
    @InjectMocks
    private AdminPropertyController adminPropertyController;
    @Mock
    private PropertyService propertyService;

    @Test
    void shouldReturnCorrectResponseWhenGetProperties() {
        when(propertyService.getProperties(anySet())).thenReturn(Map.of(
                "property1", "value1",
                "property2", "value2"));

        ResponseEntity<Map<String, String>> response = adminPropertyController.getProperties(Set.of("property1", "property2"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("value1", response.getBody().get("property1"));
        assertEquals("value2", response.getBody().get("property2"));
    }

    @Test
    void shouldReturnCorrectResponseWhenUpdateProperty() {
        ResponseEntity<Void> response = adminPropertyController.updateProperty(ConfigProperty.ADAPTIVE_DESCRIPTION, new PropertyValueDto("updated value"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
}
