package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.test.RequestTestingDetailTestDto;
import com.example.astraapi.dto.test.RequestTestingTestDto;
import com.example.astraapi.dto.testing.TestingDetailDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.TestingTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Endpoint.ADMIN_TESTINGS_TESTS)
@RequiredArgsConstructor
public class AdminTestingTestController {
    private final TestingTestService testingTestService;

    @PostMapping
    public IdDto save(@Valid @RequestBody RequestTestingTestDto dto) {
        return testingTestService.save(dto);
    }

    @PostMapping("/details")
    public IdDto save(@Valid @RequestBody RequestTestingDetailTestDto dto) {
        return testingTestService.save(dto);
    }

    @DeleteMapping
    public void deleteTest(@RequestBody RequestTestingTestDto dto) {
        testingTestService.delete(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable("id") Long id) {
        testingTestService.delete(id);
    }

    @GetMapping("/{testId}/testings")
    public List<TestingDetailDto> getTestings(@PathVariable("testId") Long testId) {
        return testingTestService.getTestings(testId);
    }
}
