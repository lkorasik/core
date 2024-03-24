package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.CourseGateway;
import ru.urfu.mm.domainlegacy.SpecialCourse;

import java.util.List;

public class GetAllCourses {
    private final CourseGateway courseGateway;

    public GetAllCourses(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
    }

    public List<SpecialCourse> getAllCourses() {
        return courseGateway.getAllCourses();
    }
}
