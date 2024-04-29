package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.SelectedCourses;
import ru.urfu.mm.domain.Course;

import java.util.List;
import java.util.UUID;

public interface CourseGateway {
    List<Course> getAllCourses();
    List<Course> getEducationalModuleCourses(UUID moduleId);
    List<SelectedCourses> getSelectedCourses(UUID studentId);
    List<ProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemestersByEducationalProgram(UUID educationalProgramId);
    List<ProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemestersBySemesters(List<UUID> semestersIds);
    List<ProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemesterByModule(UUID moduleId);
    List<UUID> getStudentBySelectedCourse(UUID courseId);
    List<ProgramToCoursesWithSemesters> getRequiredCoursesForProgram(UUID programId);
    void deleteSelectedAllById(List<UUID> uuids);
    void saveSelectedCourses(List<SelectedCourses> courses);
    Course getById(UUID id);
    void save(Course specialCourse);
    void delete(UUID id);
}
