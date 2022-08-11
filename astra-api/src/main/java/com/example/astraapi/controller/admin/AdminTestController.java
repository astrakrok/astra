package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestDto;
import com.example.astraapi.dto.TestFullDetailDto;
import com.example.astraapi.dto.TestShortDetailDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Endpoint.ADMIN_TESTS)
@RequiredArgsConstructor
public class AdminTestController {
  private final TestService testService;

  @PostMapping
  public IdDto save(@Valid @RequestBody RequestTestDto testDto) {
    return testService.save(testDto);
  }

  @GetMapping
  public List<TestShortDetailDto> getAll() {
    return testService.getAll();
  }

  @GetMapping("/{id}")
  public Optional<TestFullDetailDto> getDetailedTest(@PathVariable("id") Long id) {
    return testService.getDetailedTest(id);
  }
}
