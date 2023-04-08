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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(Endpoint.ADMIN_TESTS)
@RequiredArgsConstructor
public class AdminTestController {
    private final TestService testService;

    @PostMapping
    public ResponseEntity<TestFullDetailDto> save(@RequestBody RequestTestDto testDto) {
        TestFullDetailDto test = testService.save(testDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(test);
    }

    @PostMapping("/draft")
    public ResponseEntity<TestFullDetailDto> saveDraft(@RequestBody RequestTestDto testDto) {
        TestFullDetailDto test = testService.saveDraft(testDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(test);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<TestFullDetailDto>> update(@PathVariable("id") Long id, @RequestBody RequestTestDto testDto) {
        Optional<TestFullDetailDto> test = testService.update(id, testDto);
        return ResponseEntity.ok(test);
    }

    @PutMapping("/{id}/draft")
    public ResponseEntity<Optional<TestFullDetailDto>> updateDraft(@PathVariable("id") Long id, @RequestBody RequestTestDto testDto) {
        Optional<TestFullDetailDto> test = testService.updateDraft(id, testDto);
        return ResponseEntity.ok(test);
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<TestShortDetailDto>> getAll(
            @RequestBody AdminTestFilterDto filter,
            @Valid Pageable pageable
    ) {
        Page<TestShortDetailDto> page = testService.getAll(filter, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TestFullDetailDto>> getDetailedTest(@PathVariable("id") Long id) {
        Optional<TestFullDetailDto> test = testService.getDetailedTest(id);
        return ResponseEntity.ok(test);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable("id") Long id) {
        testService.deleteTest(id);
        return ResponseEntity.ok().build();
    }
}
