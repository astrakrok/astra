package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.PropertyValueDto;
import com.example.astraapi.meta.ConfigProperty;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(Endpoint.ADMIN_PROPERTIES)
@RequiredArgsConstructor
public class AdminPropertyController {
  private final PropertyService propertyService;

  @GetMapping
  public Map<String, String> getProperties(@RequestParam("names") Set<String> names) {
    return propertyService.getProperties(names);
  }

  @PutMapping("/{name}")
  public void updateProperty(@PathVariable("name") ConfigProperty property, @RequestBody PropertyValueDto valueDto) {
    propertyService.update(property, valueDto);
  }
}
