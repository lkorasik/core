package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.domain.EducationalProgramToCoursesWithSemesters;
import ru.urfu.mm.domain.SpecialCourse;
import ru.urfu.mm.domain.Student;

import java.util.List;
import java.util.UUID;

public class LoadAvailableCourses {
    private final StudentGateway studentGateway;
    private final CourseGateway courseGateway;

    public LoadAvailableCourses(StudentGateway studentGateway, CourseGateway courseGateway) {
        this.studentGateway = studentGateway;
        this.courseGateway = courseGateway;
    }

    public List<SpecialCourse> loadAvailableCourses(UUID studentId) {
        /*
        1. Берем студента
        2. Достаем все модули, которые доступны для данного учебного плана
         */

        Student student = studentGateway.getById(studentId);
        List<EducationalProgramToCoursesWithSemesters> educationalProgramToCoursesWithSemestersByEducationalProgram =
                courseGateway.getEducationalProgramToCoursesWithSemestersByEducationalProgram(student.getEducationalProgram().getId());
        List<SpecialCourse> courses = educationalProgramToCoursesWithSemestersByEducationalProgram
                .stream()
                .map(EducationalProgramToCoursesWithSemesters::getSpecialCourse)
                .toList();

        return courses;
    }
}
