package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Course;

import java.util.List;
import java.util.UUID;

public interface CourseGateway {
    List<Course> getAllCourses();
    List<Course> getEducationalModuleCourses(UUID moduleId);
    List<UUID> getStudentBySelectedCourse(UUID courseId);
    Course getById(UUID id);
    void save(Course specialCourse);
    void delete(UUID id);
}
