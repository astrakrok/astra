package com.example.astraapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestingPage<T> extends Page<T> {
    private Long testingId;

    public TestingPage(List<T> items, long rows, long pageSize, Long testingId) {
        super(items, rows, pageSize);
        this.testingId = testingId;
    }

    @Override
    protected <R, P extends Page<R>> P pageCreator(List<R> items) {
        return (P) new TestingPage<>(items, getRows(), getPageSize(), testingId);
    }
}
