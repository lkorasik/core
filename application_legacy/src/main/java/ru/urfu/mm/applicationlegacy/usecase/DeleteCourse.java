package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.CourseGateway;

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
