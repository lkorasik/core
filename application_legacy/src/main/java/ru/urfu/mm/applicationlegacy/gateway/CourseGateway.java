package ru.urfu.mm.applicationlegacy.gateway;

import ru.urfu.mm.domainlegacy.SpecialCourse;

import java.util.List;
import java.util.UUID;

public interface CourseGateway {
    List<SpecialCourse> getAllCourses();
    List<SpecialCourse> getEducationalModuleCourses(UUID moduleId);
}
