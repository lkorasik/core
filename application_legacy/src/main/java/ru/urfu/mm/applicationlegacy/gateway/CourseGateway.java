package ru.urfu.mm.applicationlegacy.gateway;

import ru.urfu.mm.domainlegacy.EducationalProgramToCoursesWithSemesters;
import ru.urfu.mm.domainlegacy.SelectedCourses;
import ru.urfu.mm.domainlegacy.SpecialCourse;

import java.util.List;
import java.util.UUID;

public interface CourseGateway {
    List<SpecialCourse> getAllCourses();
    List<SpecialCourse> getEducationalModuleCourses(UUID moduleId);
    List<SelectedCourses> getSelectedCourses(UUID studentId);
    List<EducationalProgramToCoursesWithSemesters> getEducationalProgramToCoursesWithSemestersByEducationalProgram(UUID educationalProgramId);
    List<EducationalProgramToCoursesWithSemesters> getRequiredCoursesForProgram(UUID programId);
    void deleteSelectedAllById(List<UUID> uuids);
    void saveSelectedCourses(List<SelectedCourses> courses);
    SpecialCourse getById(UUID id);
    void save(SpecialCourse specialCourse);
    void delete(UUID id);
}
