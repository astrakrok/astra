package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.filter.AdminTestFilterDto;
import com.example.astraapi.dto.test.RequestTestDto;
import com.example.astraapi.dto.test.TestFullDetailDto;
import com.example.astraapi.dto.test.TestShortDetailDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(Endpoint.ADMIN_TESTS)
@RequiredArgsConstructor
public class AdminTestController {
    private final TestService testService;

    @PostMapping
    public TestFullDetailDto save(@RequestBody RequestTestDto testDto) {
        return testService.save(testDto);
    }

    @PostMapping("/draft")
    public TestFullDetailDto saveDraft(@RequestBody RequestTestDto testDto) {
        return testService.saveDraft(testDto);
    }

    @PutMapping("/{id}")
    public Optional<TestFullDetailDto> update(@PathVariable("id") Long id, @RequestBody RequestTestDto testDto) {
        return testService.update(id, testDto);
    }

    @PutMapping("/{id}/draft")
    public Optional<TestFullDetailDto> updateDraft(@PathVariable("id") Long id, @RequestBody RequestTestDto testDto) {
        return testService.updateDraft(id, testDto);
    }

    @PostMapping("/filter")
    public Page<TestShortDetailDto> getAll(
            @RequestBody AdminTestFilterDto filter,
            @Valid Pageable pageable
    ) {
        return testService.getAll(filter, pageable);
    }

    @GetMapping("/{id}")
    public Optional<TestFullDetailDto> getDetailedTest(@PathVariable("id") Long id) {
        return testService.getDetailedTest(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable("id") Long id) {
        testService.deleteTest(id);
    }
}
