package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.domain.Course;

import java.util.List;

public class GetAllCourses {
    private final CourseGateway courseGateway;

    public GetAllCourses(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
    }

    public List<Course> getAllCourses() {
        return courseGateway.getAllCourses();
    }
}
