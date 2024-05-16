package ru.urfu.mm.application.usecase.get_modules_courses;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.domain.Course;

import java.util.List;

public class GetModulesCourses {
    private final CourseGateway courseGateway;

    public GetModulesCourses(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
    }

    public List<Course> getModulesCourses() {
        return courseGateway.getAllCourses();
    }
}
