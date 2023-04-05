package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.PropertyValueDto;
import com.example.astraapi.meta.ConfigProperty;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(Endpoint.ADMIN_PROPERTIES)
@RequiredArgsConstructor
public class AdminPropertyController {
    private final PropertyService propertyService;

    @GetMapping
    public ResponseEntity<Map<String, String>> getProperties(@RequestParam("names") Set<String> names) {
        Map<String, String> properties = propertyService.getProperties(names);
        return ResponseEntity.ok(properties);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Void> updateProperty(@PathVariable("name") ConfigProperty property, @RequestBody PropertyValueDto valueDto) {
        propertyService.update(property, valueDto);
        return ResponseEntity.ok().build();
    }
}
