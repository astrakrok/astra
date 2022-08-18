package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestDto;
import com.example.astraapi.dto.TestFullDetailDto;
import com.example.astraapi.dto.TestShortDetailDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

  @PutMapping("/{id}")
  public void update(@PathVariable("id") Long id, @Valid @RequestBody RequestTestDto testDto) {
    testService.update(id, testDto);
  }

  @GetMapping
  public Page<TestShortDetailDto> getAll(@Valid Pageable pageable) {
    return testService.getAll(pageable);
  }

  @GetMapping("/{id}")
  public Optional<TestFullDetailDto> getDetailedTest(@PathVariable("id") Long id) {
    return testService.getDetailedTest(id);
  }
}
