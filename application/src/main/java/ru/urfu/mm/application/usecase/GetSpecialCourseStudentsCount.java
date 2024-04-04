package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.CourseGateway;

import java.util.UUID;

public class GetSpecialCourseStudentsCount {
    private final CourseGateway courseGateway;

    public GetSpecialCourseStudentsCount(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
    }

    public int specialCourseStudentsCount(UUID courseId) {
        return courseGateway.getStudentBySelectedCourse(courseId).size();
    }
}
