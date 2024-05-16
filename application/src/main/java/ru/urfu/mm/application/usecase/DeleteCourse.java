package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.CourseGateway;

import java.util.UUID;

public class DeleteCourse {
    private final CourseGateway courseGateway;

    public DeleteCourse(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
    }

    public void deleteCourse(UUID courseId) {
        courseGateway.delete(courseId);
    }
}
