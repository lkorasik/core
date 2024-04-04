package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.CourseGateway;
import ru.urfu.mm.domainlegacy.SpecialCourse;

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
