package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.test.RequestTestingDetailTestDto;
import com.example.astraapi.dto.test.RequestTestingTestDto;
import com.example.astraapi.dto.testing.TestingDetailDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.TestingTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Endpoint.ADMIN_TESTINGS_TESTS)
@RequiredArgsConstructor
public class AdminTestingTestController {
    private final TestingTestService testingTestService;

    @PostMapping
    public ResponseEntity<IdDto> save(@Valid @RequestBody RequestTestingTestDto dto) {
        IdDto idDto = testingTestService.save(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(idDto);
    }

    @PostMapping("/details")
    public ResponseEntity<IdDto> save(@Valid @RequestBody RequestTestingDetailTestDto dto) {
        IdDto idDto = testingTestService.save(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(idDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTest(@RequestBody RequestTestingTestDto dto) {
        testingTestService.delete(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable("id") Long id) {
        testingTestService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{testId}/testings")
    public ResponseEntity<List<TestingDetailDto>> getTestings(@PathVariable("testId") Long testId) {
        List<TestingDetailDto> testings = testingTestService.getTestings(testId);
        return ResponseEntity.ok(testings);
    }
}
