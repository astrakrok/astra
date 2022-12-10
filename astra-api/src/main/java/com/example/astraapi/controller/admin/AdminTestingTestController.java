package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.test.RequestTestingTestDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.TestingTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.ADMIN_TESTINGS_TESTS)
@RequiredArgsConstructor
public class AdminTestingTestController {
  private final TestingTestService testingTestService;

  @PostMapping
  public IdDto save(@Valid @RequestBody RequestTestingTestDto dto) {
    return testingTestService.save(dto);
  }

  @DeleteMapping("/{id}")
  public void deleteTest(@PathVariable("id") Long id) {
    testingTestService.delete(id);
  }
}
