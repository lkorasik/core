package ru.urfu.mm.application.usecase.update_study_plan;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.application.usecase.get_program_by_id.GetProgramById;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.Syllabus;

import java.util.UUID;

/**
 * Обновление учебного плана
 * 1. Достаем программу
 * 2. Достаем учебный план, который соответствует программе и нужному учебному году
 * 3. Достаем нужные курсы
 */
public class UpdateStudyPlan {
    private final StudyPlanGateway studyPlanGateway;
    private final GetProgramById getProgramById;
    private final CourseGateway courseGateway;

    public UpdateStudyPlan(
            StudyPlanGateway studyPlanGateway,
            GetProgramById getProgramById,
            CourseGateway courseGateway) {
        this.studyPlanGateway = studyPlanGateway;
        this.getProgramById = getProgramById;
        this.courseGateway = courseGateway;
    }

    public void update(UpdateStudyPlanRequest request) {
        EducationalProgram program = getProgramById.getProgramById(request.programId());

        Syllabus syllabus = studyPlanGateway.findAllByProgram(program)
                .stream()
                .filter(x -> x.getFirstSemesterPlan().getSemester().getYear() == request.startYear())
                .findFirst()
                .get();


//        courseGateway.getAllCourses()
//                .stream()
//                .filter(x -> request.courses().)
//                .toList();
        System.out.println(syllabus);
    }
}
