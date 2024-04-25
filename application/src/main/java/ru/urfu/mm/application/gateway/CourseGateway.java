package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.EducationalProgramToCoursesWithSemesters;
import ru.urfu.mm.domain.SelectedCourses;
import ru.urfu.mm.domain.Course;

import java.util.List;
import java.util.UUID;

public interface CourseGateway {
    List<Course> getAllCourses();
    List<Course> getEducationalModuleCourses(UUID moduleId);
    List<SelectedCourses> getSelectedCourses(UUID studentId);
    List<EducationalProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemestersByEducationalProgram(UUID educationalProgramId);
    List<EducationalProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemestersBySemesters(List<UUID> semestersIds);
    List<EducationalProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemesterByModule(UUID moduleId);
    List<UUID> getStudentBySelectedCourse(UUID courseId);
    List<EducationalProgramToCoursesWithSemesters> getRequiredCoursesForProgram(UUID programId);
    void deleteSelectedAllById(List<UUID> uuids);
    void saveSelectedCourses(List<SelectedCourses> courses);
    Course getById(UUID id);
    void save(Course specialCourse);
    void delete(UUID id);
}
