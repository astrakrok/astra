package com.example.astraapi.service.impl;

import com.example.astraapi.config.AdaptiveProperties;
import com.example.astraapi.dto.filter.StepsStatisticFilterDto;
import com.example.astraapi.dto.statistic.SpecializationStatisticDto;
import com.example.astraapi.dto.statistic.StepStatisticDto;
import com.example.astraapi.dto.statistic.SubjectStatisticDto;
import com.example.astraapi.dto.test.AdaptiveTestDto;
import com.example.astraapi.entity.projection.TestIdAndSubjectsIdsProjection;
import com.example.astraapi.mapper.TestMapper;
import com.example.astraapi.repository.TestRepository;
import com.example.astraapi.service.AdaptiveTestService;
import com.example.astraapi.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdaptiveTestServiceImpl implements AdaptiveTestService {
    private final StatisticService statisticService;
    private final TestMapper testMapper;
    private final TestRepository testRepository;
    private final AdaptiveProperties adaptiveProperties;

    @Override
    public List<AdaptiveTestDto> getAdaptiveTests(long specializationId) {
        List<SubjectStatisticDto> subjects = getSubjects(specializationId);
        List<SubjectStatisticDto> topFailedSubjects = subjects.stream()
                .limit(adaptiveProperties.getSubjectsCount())
                .collect(Collectors.toList());
        List<Long> idsForTopFailedSubjects = getTests(topFailedSubjects);
        List<Long> restTestsIds = getRestTestsIds(subjects, idsForTopFailedSubjects);
        return getAllTests(idsForTopFailedSubjects, restTestsIds);
    }

    private List<AdaptiveTestDto> getAllTests(List<Long> first, List<Long> second) {
        first.addAll(second);
        return testRepository.getFullDetailedTestsByIds(first).stream()
                .map(testMapper::toAdaptiveTestDto)
                .collect(Collectors.toList());
    }

    private List<Long> getTests(List<SubjectStatisticDto> subjects) {
        List<Long> testsIds = new ArrayList<>();
        Map<Long, Long> subjectIdToTestsCount = new HashMap<>();
        long failedTestsCount = getFailedTestsCount(subjects);
        for (SubjectStatisticDto statistic : subjects) {
            long failedCount = statistic.getStatistic().getTotalCount() - statistic.getStatistic().getCorrectCount();
            if (failedCount == 0) {
                continue;
            }
            long testsNeeded = Math.round(failedCount * 100.0 / failedTestsCount) - subjectIdToTestsCount.getOrDefault(statistic.getId(), 0L);
            testsNeeded = Math.min(adaptiveProperties.getCount() - testsIds.size(), testsNeeded);
            if (testsNeeded <= 0) {
                continue;
            }
            List<TestIdAndSubjectsIdsProjection> result = testRepository.getActiveTestsIdsBySubjectAndExceptIds(List.of(statistic.getId()), testsIds, testsNeeded);
            for (TestIdAndSubjectsIdsProjection item : result) {
                testsIds.add(item.getId());
                for (Long subjectId : item.getSubjectsIds()) {
                    Long testsCount = subjectIdToTestsCount.getOrDefault(subjectId, 0L);
                    subjectIdToTestsCount.put(subjectId, testsCount + 1);
                }
            }
        }
        return testsIds;
    }

    private List<Long> getRestTestsIds(List<SubjectStatisticDto> subjects, List<Long> mainTestsIds) {
        List<Long> subjectsIds = subjects.stream()
                .map(SubjectStatisticDto::getId)
                .collect(Collectors.toList());
        int neededCount = adaptiveProperties.getCount() - mainTestsIds.size();
        return testRepository.getActiveTestsIdsBySubjectAndExceptIds(subjectsIds, mainTestsIds, (long) neededCount).stream()
                .map(TestIdAndSubjectsIdsProjection::getId)
                .collect(Collectors.toList());
    }

    private long getFailedTestsCount(List<SubjectStatisticDto> subjects) {
        return subjects.stream()
                .map(SubjectStatisticDto::getStatistic)
                .map(statistic -> statistic.getTotalCount() - statistic.getCorrectCount())
                .reduce(Long::sum)
                .orElse(0L);
    }

    private List<SubjectStatisticDto> getSubjects(long specializationId) {
        return statisticService.getStepsStatistic(new StepsStatisticFilterDto(specializationId)).stream()
                .map(StepStatisticDto::getSpecializations)
                .flatMap(List::stream)
                .map(SpecializationStatisticDto::getSubjects)
                .flatMap(List::stream)
                .sorted(this::compareSubjects)
                .collect(Collectors.toList());
    }

    private int compareSubjects(SubjectStatisticDto first, SubjectStatisticDto second) {
        if (first.getStatistic().getTotalCount() == 0) {
            return 1;
        } else if (second.getStatistic().getTotalCount() == 0) {
            return -1;
        }
        double firstSubjectCorrectness = first.getStatistic().getCorrectCount() * 1.0 / first.getStatistic().getTotalCount();
        double secondSubjectCorrectness = second.getStatistic().getCorrectCount() * 1.0 / second.getStatistic().getTotalCount();
        if (Math.abs(firstSubjectCorrectness - secondSubjectCorrectness) < 0.00000001) {
            return first.getStatistic().getTotalCount() > second.getStatistic().getTotalCount() ? -1 : 1;
        }
        return firstSubjectCorrectness > secondSubjectCorrectness ? 1 : -1;
    }
}
