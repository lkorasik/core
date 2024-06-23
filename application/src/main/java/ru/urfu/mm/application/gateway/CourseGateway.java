package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Course;
import ru.urfu.mm.domain.EducationalModule;

import java.util.List;
import java.util.UUID;

public interface CourseGateway {
    List<Course> getEducationalModuleCourses(UUID moduleId);
    Course getById(UUID id);
    void save(EducationalModule module, Course specialCourse);
    void delete(UUID id);
}
