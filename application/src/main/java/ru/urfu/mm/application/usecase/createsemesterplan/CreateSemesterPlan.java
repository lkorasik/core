package ru.urfu.mm.application.usecase.createsemesterplan;

import ru.urfu.mm.application.gateway.SemesterPlanGateway;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.domain.SemesterPlan;

import java.util.UUID;

/**
 * Создаем план на семестр
 */
public class CreateSemesterPlan {
    private final SemesterPlanGateway semesterPlanGateway;

    public CreateSemesterPlan(SemesterPlanGateway semesterPlanGateway) {
        this.semesterPlanGateway = semesterPlanGateway;
    }

    public SemesterPlan createSemesterPlan(Semester semester) {
        SemesterPlan semesterPlan = new SemesterPlan(UUID.randomUUID(), semester, 0);
        semesterPlanGateway.save(semesterPlan);
        return semesterPlan;
    }
}
