package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.domain.Course;

import java.util.List;
import java.util.UUID;

public class GetEducationalModuleCourses {
    private final CourseGateway courseGateway;

    public GetEducationalModuleCourses(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
    }

    public List<Course> getEducationalModuleCourses(UUID moduleId) {
        return courseGateway.getEducationalModuleCourses(moduleId);
    }
}
