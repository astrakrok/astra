package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
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
    public IdDto save(@RequestBody RequestTestDto testDto) {
        return testService.save(testDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody RequestTestDto testDto) {
        testService.update(id, testDto);
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
}
