package ru.urfu.mm.application.usecase.create_study_plan;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.SemesterGateway;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.application.usecase.create_semester_plan.CreateSemesterPlan;
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
    private final CreateSemesterPlan createSemesterPlan;

    public CreateStudyPlan(
            SemesterGateway semesterGateway,
            StudyPlanGateway studyPlanGateway,
            ProgramGateway programGateway,
            CreateSemesterPlan createSemesterPlan) {
        this.semesterGateway = semesterGateway;
        this.studyPlanGateway = studyPlanGateway;
        this.programGateway = programGateway;
        this.createSemesterPlan = createSemesterPlan;
    }

    public void createStudyPlan(int startYear, UUID programId) {
        List<SemesterPlan> semesterPlans = semesterGateway.getSemestersForEntireStudyPeriod(startYear)
                .stream()
                .map(createSemesterPlan::createSemesterPlan)
                .toList();

        Program program = programGateway.getById(programId);

        StudyPlan studyPlan = new StudyPlan(
                UUID.randomUUID(),
                semesterPlans.get(0),
                semesterPlans.get(1),
                semesterPlans.get(2),
                semesterPlans.get(3)
        );
        program.getStudyPlans().add(studyPlan);

        studyPlanGateway.save(studyPlan, program);
        programGateway.save(program);
    }
}
