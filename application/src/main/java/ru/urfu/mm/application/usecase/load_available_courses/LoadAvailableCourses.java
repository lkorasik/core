package ru.urfu.mm.application.usecase.load_available_courses;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.domain.ProgramToCoursesWithSemesters;
import ru.urfu.mm.domain.Module;
import ru.urfu.mm.domain.Course;
import ru.urfu.mm.domain.Student;

import java.util.*;
import java.util.stream.Collectors;

public class LoadAvailableCourses {
    private final StudentGateway studentGateway;
    private final CourseGateway courseGateway;

    public LoadAvailableCourses(StudentGateway studentGateway, CourseGateway courseGateway) {
        this.studentGateway = studentGateway;
        this.courseGateway = courseGateway;
    }

    public List<AvailableModuleResponse> loadAvailableCourses(UUID studentId) {
        /*
        1. Берем студента
        2. Достаем все модули, которые доступны для данного учебного плана
         */

        Student student = studentGateway.getById(studentId);
        List<ProgramToCoursesWithSemesters> educationalProgramToCoursesWithSemestersByProgram =
                courseGateway.getEducationalProgramToCoursesWithSemestersByEducationalProgram(student.getEducationalProgram().getId());
        List<Course> courses = educationalProgramToCoursesWithSemestersByProgram
                .stream()
                .map(ProgramToCoursesWithSemesters::getSpecialCourse)
                .toList();
        Map<Module, List<Course>> moduleToCourses = courses
                .stream()
                .collect(Collectors.groupingBy(Course::getEducationalModule));

        List<AvailableModuleResponse> modules = new ArrayList<>();
        moduleToCourses.forEach((module, courses1) -> {
            AvailableModuleResponse moduleResponse = new AvailableModuleResponse(
                    module.getId(),
                    module.getName(),
                    courses1.stream()
                            .map(y -> new AvailableCourseResponse(y.getId(), y.getName()))
                            .toList()
            );
            modules.add(moduleResponse);
        });

        return modules;
    }
}
