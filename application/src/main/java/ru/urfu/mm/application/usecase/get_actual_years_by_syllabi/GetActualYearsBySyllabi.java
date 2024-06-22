package ru.urfu.mm.application.usecase.get_actual_years_by_syllabi;

import ru.urfu.mm.application.gateway.BaseSyllabusPlanGateway;
import ru.urfu.mm.domain.BaseSemesterPlan;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.domain.Semester;

import java.util.List;
import java.util.UUID;

/**
 * Получить список актуальных годов основываясь на базовых учебных планах
 */
public class GetActualYearsBySyllabi {
    private final BaseSyllabusPlanGateway baseSyllabusPlanGateway;

    public GetActualYearsBySyllabi(BaseSyllabusPlanGateway baseSyllabusPlanGateway) {
        this.baseSyllabusPlanGateway = baseSyllabusPlanGateway;
    }

    public List<Integer> getActualYearsBySyllabi(UUID programId) {
        return baseSyllabusPlanGateway.getAllSyllabi(programId)
                .stream()
                .map(BaseSyllabus::getFirstSemesterPlan)
                .map(BaseSemesterPlan::getSemester)
                .map(Semester::getYear)
                .toList();
    }
}
