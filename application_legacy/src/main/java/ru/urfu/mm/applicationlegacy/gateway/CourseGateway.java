package ru.urfu.mm.applicationlegacy.gateway;

import ru.urfu.mm.domainlegacy.SpecialCourse;

import java.util.List;

public interface CourseGateway {
    List<SpecialCourse> getAllCourses();
}
