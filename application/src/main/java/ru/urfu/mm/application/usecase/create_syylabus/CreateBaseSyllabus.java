package ru.urfu.mm.application.usecase.create_syylabus;

import ru.urfu.mm.application.gateway.BaseSyllabusPlanGateway;
import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.domain.BaseSemesterPlan;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.domain.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Создаем базовый учебный план.
 * 1. Получить базовый учебный план для конкретной программы
 */
public class CreateBaseSyllabus {
    private final BaseSyllabusPlanGateway baseSyllabusPlanGateway;
    private final CourseGateway courseGateway;

    public CreateBaseSyllabus(BaseSyllabusPlanGateway baseSyllabusPlanGateway, CourseGateway courseGateway) {
        this.baseSyllabusPlanGateway = baseSyllabusPlanGateway;
        this.courseGateway = courseGateway;
    }

    public void createStudyPlan(CreateSyllabusRequest createSyllabusRequest) {
        // todo: реализуй сохранение базового плана
        // Надо достать семестровые планы

        BaseSyllabus syllabus = baseSyllabusPlanGateway.getAllSyllabi(createSyllabusRequest.programId())
                .stream()
                .filter(x -> x.getFirstSemesterPlan().getSemester().getId().equals(createSyllabusRequest.firstSemesterId()))
                .findFirst()
                .get();


        List<Course> firstSemesterRequiredCourses = new ArrayList<>();
        List<Course> secondSemesterRequiredCourses = new ArrayList<>();
        List<Course> thirdSemesterRequiredCourses = new ArrayList<>();
        List<Course> fourthSemesterRequiredCourses = new ArrayList<>();
        createSyllabusRequest.modules()
                .forEach(module -> module.courses()
                        .forEach(course -> {
                            Course fullCourse = courseGateway.getById(course.courseId());
                            if (syllabus.getFirstSemesterPlan().getSemester().getId().equals(course.semesterId())) {
                                firstSemesterRequiredCourses.add(fullCourse);
                            }
                            if (syllabus.getSecondSemesterPlan().getSemester().getId().equals(course.semesterId())) {
                                secondSemesterRequiredCourses.add(fullCourse);
                            }
                            if (syllabus.getThirdSemesterPlan().getSemester().getId().equals(course.semesterId())) {
                                thirdSemesterRequiredCourses.add(fullCourse);
                            }
                            if (syllabus.getFourthSemesterPlan().getSemester().getId().equals(course.semesterId())) {
                                fourthSemesterRequiredCourses.add(fullCourse);
                            }
                        })
                );

        BaseSemesterPlan firstSemesterPlan = new BaseSemesterPlan(
                syllabus.getFirstSemesterPlan().getId(),
                syllabus.getFirstSemesterPlan().getSemester(),
                firstSemesterRequiredCourses,
                syllabus.getFirstSemesterPlan().getAvailableCourses(),
                syllabus.getFirstSemesterPlan().getScienceWorks()
        );
        BaseSemesterPlan secondSemesterPlan = new BaseSemesterPlan(
                syllabus.getSecondSemesterPlan().getId(),
                syllabus.getSecondSemesterPlan().getSemester(),
                secondSemesterRequiredCourses,
                syllabus.getSecondSemesterPlan().getAvailableCourses(),
                syllabus.getSecondSemesterPlan().getScienceWorks()
        );
        BaseSemesterPlan thirdSemesterPlan = new BaseSemesterPlan(
                syllabus.getThirdSemesterPlan().getId(),
                syllabus.getThirdSemesterPlan().getSemester(),
                thirdSemesterRequiredCourses,
                syllabus.getThirdSemesterPlan().getAvailableCourses(),
                syllabus.getThirdSemesterPlan().getScienceWorks()
        );
        BaseSemesterPlan fourthSemesterPlan = new BaseSemesterPlan(
                syllabus.getFourthSemesterPlan().getId(),
                syllabus.getFourthSemesterPlan().getSemester(),
                fourthSemesterRequiredCourses,
                syllabus.getFourthSemesterPlan().getAvailableCourses(),
                syllabus.getFourthSemesterPlan().getScienceWorks()
        );
        BaseSyllabus newSyllabus = new BaseSyllabus(
                syllabus.getId(),
                firstSemesterPlan,
                secondSemesterPlan,
                thirdSemesterPlan,
                fourthSemesterPlan
        );

        baseSyllabusPlanGateway.save(newSyllabus);
    }
}
