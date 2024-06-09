package ru.urfu.mm.application.usecase.update_study_plan;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.gateway.StudyPlanGateway;
import ru.urfu.mm.application.usecase.get_program_by_id.GetProgramById;
import ru.urfu.mm.domain.Course;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.Syllabus;

import java.util.List;
import java.util.UUID;

/**
 * Обновление учебного плана
 * 1. Достаем программу
 * 2. Достаем учебный план, который соответствует программе и нужному учебному году
 * 3. Достаем нужные курсы для каждого семестра
 * 4. Добавляем курсы первого семестра в первый семестр учебного плана. Аналогично для остальных семестров.
 * 5. Сохранить новый учебный план
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

        List<Course> firstSemester = request.courses()
                .stream()
                .filter(x -> x.semesterNumber() == 0)
                .map(x -> courseGateway.getById(x.courseId()))
                .toList();
        List<Course> secondSemester = request.courses()
                .stream()
                .filter(x -> x.semesterNumber() == 1)
                .map(x -> courseGateway.getById(x.courseId()))
                .toList();
        List<Course> thirdSemester = request.courses()
                .stream()
                .filter(x -> x.semesterNumber() == 2)
                .map(x -> courseGateway.getById(x.courseId()))
                .toList();
        List<Course> fourthSemester = request.courses()
                .stream()
                .filter(x -> x.semesterNumber() == 3)
                .map(x -> courseGateway.getById(x.courseId()))
                .toList();

        syllabus.getFirstSemesterPlan().getRequiredCourses().addAll(firstSemester);
        syllabus.getSecondSemesterPlan().getRequiredCourses().addAll(secondSemester);
        syllabus.getThirdSemesterPlan().getRequiredCourses().addAll(thirdSemester);
        syllabus.getFourthSemesterPlan().getRequiredCourses().addAll(fourthSemester);

        studyPlanGateway.save(syllabus, program);
    }
}
