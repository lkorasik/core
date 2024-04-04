package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.domain.SpecialCourse;

import java.util.UUID;

public class GetCourse {
    private final CourseGateway courseGateway;

    public GetCourse(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
    }

    public SpecialCourse getCourse(UUID specialCourseId) {
        return courseGateway.getById(specialCourseId);
    }
}
