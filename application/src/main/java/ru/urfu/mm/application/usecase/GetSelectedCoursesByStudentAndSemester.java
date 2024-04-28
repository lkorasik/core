package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.domain.ProgramToCoursesWithSemesters;
import ru.urfu.mm.domain.Student;

import java.util.List;
import java.util.UUID;

/**
 * Получить список выбранных курсов для конкретного студента и конкретного семестра
 */
public class GetSelectedCoursesByStudentAndSemester {
    private final CourseGateway courseGateway;
    private final StudentGateway studentGateway;

    public GetSelectedCoursesByStudentAndSemester(CourseGateway courseGateway, StudentGateway studentGateway) {
        this.courseGateway = courseGateway;
        this.studentGateway = studentGateway;
    }

    public List<ProgramToCoursesWithSemesters> getSelectedCoursesByStudentAndSemester(UUID studentId, UUID semesterId) {
        Student student = studentGateway.getById(studentId);

        return courseGateway
                .getEducationalProgramToCoursesWithSemestersByEducationalProgram(student.getEducationalProgram().getId())
                .stream()
                .filter(x -> x.getSemester().getId().equals(semesterId))
                .toList();
    }
}
