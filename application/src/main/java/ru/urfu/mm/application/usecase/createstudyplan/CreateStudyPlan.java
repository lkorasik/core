package ru.urfu.mm.application.usecase.createstudyplan;

import ru.urfu.mm.application.gateway.SemesterGateway;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.domain.SemesterPlan;
import ru.urfu.mm.domain.StudyPlan;

import java.util.ArrayList;
import java.util.List;

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

    public void createStudyPlan(int startYear, List<Integer> credits) {
        List<Semester> semesters = semesterGateway.getSemestersForEntireStudyPeriod(startYear);

        List<SemesterPlan> plans = new ArrayList<>(4);
        for(var i = 0; i < 4; i++) {
            SemesterPlan plan = new SemesterPlan(semesters.get(i), credits.get(i));
            plans.add(plan);
        }

        StudyPlan studyPlan = new StudyPlan(plans.get(0), plans.get(1), plans.get(2), plans.get(3));

        studyPlanGateway.save(studyPlan);
    }
}
