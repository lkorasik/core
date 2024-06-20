package ru.urfu.mm.application.usecase.create_study_plan;

import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.SemesterGateway;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
//import ru.urfu.mm.application.usecase.create_semester_plan.CreateSemesterPlan;

import java.util.UUID;

/**
 * Создаем базовый учебный план.
 * 1. Находим нужные нам семестры.
 * 2. На каждый семестр создаем семестровый план.
 * 3. Находим программу. Добавляем туда наш план.
 * 4. Сохраняем план
 * 5. Сохраняем программу
 */
public class CreateBaseSyllabus {
    private final SemesterGateway semesterGateway;
    private final StudyPlanGateway studyPlanGateway;
    private final ProgramGateway programGateway;
//    private final CreateSemesterPlan createSemesterPlan;

    public CreateBaseSyllabus(
            SemesterGateway semesterGateway,
            StudyPlanGateway studyPlanGateway,
            ProgramGateway programGateway
//            CreateSemesterPlan createSemesterPlan
    ) {
        this.semesterGateway = semesterGateway;
        this.studyPlanGateway = studyPlanGateway;
        this.programGateway = programGateway;
//        this.createSemesterPlan = createSemesterPlan;
    }

    public void createStudyPlan(int startYear, UUID programId) {
        throw new NotImplementedException();
//        List<SemesterPlan> semesterPlans = semesterGateway.getSemestersForEntireStudyPeriod(startYear)
//                .stream()
//                .map(createSemesterPlan::createSemesterPlan)
//                .toList();

//        EducationalProgram educationalProgram = programGateway.getById(programId);

//        StudentSyllabus studentSyllabus = new StudentSyllabus(
//                UUID.randomUUID(),
//                semesterPlans.get(0),
//                semesterPlans.get(1),
//                semesterPlans.get(2),
//                semesterPlans.get(3)
//        );
//        educationalProgram.getSyllabi().add(studentSyllabus);

//        studyPlanGateway.save(studentSyllabus, educationalProgram);
//        programGateway.save(educationalProgram);
    }
}
