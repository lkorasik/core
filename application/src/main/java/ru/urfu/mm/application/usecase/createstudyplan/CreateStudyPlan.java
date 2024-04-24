package ru.urfu.mm.application.usecase.createstudyplan;

import ru.urfu.mm.application.gateway.SemesterGateway;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.domain.SemesterPlan;
import ru.urfu.mm.domain.StudyPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Создаем учебный план.
 * 1. Находим нужные нам семестры.
 * 2. На каждый семестр создаем семестровый план.
 */
public class CreateStudyPlan {
    private final SemesterGateway semesterGateway;
    private final StudyPlanGateway studyPlanGateway;

    public CreateStudyPlan(SemesterGateway semesterGateway, StudyPlanGateway studyPlanGateway) {
        this.semesterGateway = semesterGateway;
        this.studyPlanGateway = studyPlanGateway;
    }

    public void createStudyPlan(int startYear) {
        List<SemesterPlan> semesters = semesterGateway.getSemestersForEntireStudyPeriod(startYear)
                .stream()
                .map(x -> new SemesterPlan(x, 0))
                .toList();

        StudyPlan studyPlan = new StudyPlan(semesters.get(0), semesters.get(1), semesters.get(2), semesters.get(3));

        studyPlanGateway.save(studyPlan);
    }
}
