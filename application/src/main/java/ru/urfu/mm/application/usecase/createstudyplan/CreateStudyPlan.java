package ru.urfu.mm.application.usecase.createstudyplan;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.SemesterGateway;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.SemesterPlan;
import ru.urfu.mm.domain.StudyPlan;

import java.util.List;
import java.util.UUID;

/**
 * Создаем учебный план.
 * 1. Находим нужные нам семестры.
 * 2. На каждый семестр создаем семестровый план.
 * 3. Находим программу. Добавляем туда наш план.
 * 4. Сохраняем план
 * 5. Сохраняем программу
 */
public class CreateStudyPlan {
    private final SemesterGateway semesterGateway;
    private final StudyPlanGateway studyPlanGateway;
    private final ProgramGateway programGateway;

    public CreateStudyPlan(
            SemesterGateway semesterGateway,
            StudyPlanGateway studyPlanGateway,
            ProgramGateway programGateway) {
        this.semesterGateway = semesterGateway;
        this.studyPlanGateway = studyPlanGateway;
        this.programGateway = programGateway;
    }

    public void createStudyPlan(int startYear, UUID programId) {
        List<SemesterPlan> semesters = semesterGateway.getSemestersForEntireStudyPeriod(startYear)
                .stream()
                .map(x -> new SemesterPlan(UUID.randomUUID(), x, 0))
                .toList();

        Program program = programGateway.getById(programId);

        StudyPlan studyPlan = new StudyPlan(
                UUID.randomUUID(),
                semesters.get(0),
                semesters.get(1),
                semesters.get(2),
                semesters.get(3));
        program.getStudyPlans().add(studyPlan);

        programGateway.save(program);
        studyPlanGateway.save(studyPlan);
    }
}
