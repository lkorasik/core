package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Course;
import ru.urfu.mm.domain.EducationalModule;

import java.util.List;
import java.util.UUID;

public interface CourseGateway {
    Course getById(UUID id);
    void save(EducationalModule module, Course specialCourse);
}
