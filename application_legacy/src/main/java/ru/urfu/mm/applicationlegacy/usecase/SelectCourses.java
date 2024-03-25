package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.exception.CourseRequiredCriteriaException;
import ru.urfu.mm.applicationlegacy.exception.CoursesSelectionValidationException;
import ru.urfu.mm.applicationlegacy.gateway.CourseGateway;
import ru.urfu.mm.applicationlegacy.gateway.SemesterGateway;
import ru.urfu.mm.applicationlegacy.gateway.StudentGateway;
import ru.urfu.mm.domainlegacy.SelectedCourses;
import ru.urfu.mm.domainlegacy.Semester;
import ru.urfu.mm.domainlegacy.Student;

import java.util.*;
import java.util.stream.Collectors;

public class SelectCourses {
    private final CourseGateway courseGateway;
    private final SemesterGateway semesterGateway;
    private final StudentGateway studentGateway;
    private final GetCoursesByEducationalProgramAndSemesters getCoursesByEducationalProgramAndSemesters;

    public SelectCourses(
            CourseGateway courseGateway,
            SemesterGateway semesterGateway,
            StudentGateway studentGateway,
            GetCoursesByEducationalProgramAndSemesters getCoursesByEducationalProgramAndSemesters) {
        this.courseGateway = courseGateway;
        this.semesterGateway = semesterGateway;
        this.studentGateway = studentGateway;
        this.getCoursesByEducationalProgramAndSemesters = getCoursesByEducationalProgramAndSemesters;
    }

    public void select(UUID studentId, List<Map.Entry<UUID, List<UUID>>> selectedCourses) {
        Student student = studentGateway.getById(studentId);

        validateCoursesSelection(student, selectedCourses);
        saveCoursesSelection(student, selectedCourses);
    }

    private void validateCoursesSelection(Student student, List<Map.Entry<UUID, List<UUID>>> coursesBySemesters) {
        var semesterIdToValidationStatus = new HashMap<UUID, CourseSelectionValidationStatus>();
        for (var coursesBySemester : coursesBySemesters) {
            var validationResult = validateCoursesSelectionBySemester(student, coursesBySemester.getKey(), coursesBySemester.getValue());

            if (validationResult != CourseSelectionValidationStatus.OK) {
                semesterIdToValidationStatus.put(coursesBySemester.getKey(), validationResult);
            }
        }
        if (!semesterIdToValidationStatus.isEmpty()) {
            throw new CoursesSelectionValidationException(true);
        }

        var isValidForModules = validateModules(
                student,
                coursesBySemesters
                        .stream()
                        .map(Map.Entry::getValue)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
        );
        if (!isValidForModules) {
            throw new CoursesSelectionValidationException(true);
        }
    }

    private boolean validateModules(Student student, List<UUID> selectedCoursesIds) {
        var selectedCoursesIdsSet = new HashSet<>(selectedCoursesIds);

        var courses = getCoursesByEducationalProgramAndSemesters
                .getCoursesByEducationalProgramAndSemesters(student.getEducationalProgram().getId(), null)
                .stream()
                .map(x -> Map.entry(x.getEducationalModuleId(), x.getId()))
                .toList();
        for (var educationalModuleIdWithCoursesIds : courses) {
            if (educationalModuleIdWithCoursesIds.getKey() == null) {
                continue;
            }

            var moduleCoursesIds = List.of(educationalModuleIdWithCoursesIds.getValue());
            boolean doesContainModuleCourses = moduleCoursesIds
                    .stream()
                    .anyMatch(selectedCoursesIdsSet::contains);
            if (!doesContainModuleCourses) {
                continue;
            }
            boolean doesContainAllModuleCourses = selectedCoursesIdsSet
                    .containsAll(moduleCoursesIds);
            if (!doesContainAllModuleCourses) {
                return false;
            }
        }
        return true;
    }

    private CourseSelectionValidationStatus validateCoursesSelectionBySemester(Student student, UUID semesterId, List<UUID> selectedCoursesIds) {
        if (!isValidForEducationalProgram(student, semesterId, selectedCoursesIds)) {
            return CourseSelectionValidationStatus.MISSING_REQUIRED_COURSES_EXTRA_COURSES_FOUND;
        }
        return CourseSelectionValidationStatus.OK;
    }

    private boolean isValidForEducationalProgram(Student student, UUID semesterId, List<UUID> selectedCoursesIds) {
        var selectedCoursesIdsSet = new HashSet<>(selectedCoursesIds);

        var coursesIdsForEducationalProgram = getCoursesByEducationalProgramAndSemesters
                .getCoursesByEducationalProgramAndSemesters(student.getEducationalProgram().getId(), List.of(semesterId))
                .stream()
                .map(ru.urfu.mm.applicationlegacy.usecase.CourseForEducationalProgram::getId)
                .collect(Collectors.toSet());
        var requiredCoursesIds = getRequiredCoursesForEducationalProgram(student.getEducationalProgram().getId())
                .stream()
                .filter(x -> x.getValue() == semesterId)
                .map(Map.Entry::getKey)
                .toList();
        var noExtraCoursesSelected = selectedCoursesIds
                .stream()
                .map(coursesIdsForEducationalProgram::contains)
                .reduce((x, y) -> x && y)
                .get();
        var allRequiredCoursesSelected = requiredCoursesIds.isEmpty() || requiredCoursesIds
                .stream()
                .map(selectedCoursesIdsSet::contains)
                .reduce((x, y) -> x && y)
                .get();

        return noExtraCoursesSelected && allRequiredCoursesSelected;
    }

    public List<Map.Entry<UUID, UUID>> getRequiredCoursesForEducationalProgram(UUID educationalProgramId) {
        var requiredCourses = courseGateway
                .getRequiredCoursesForProgram(educationalProgramId)
                .stream()
                .map(x -> Map.entry(x.getSpecialCourse().getId(), x.getSemester().getId()))
                .toList();
        var coursesIdsHashSet = requiredCourses
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        if (requiredCourses.size() != coursesIdsHashSet.size()) {
            throw new CourseRequiredCriteriaException();
        }
        return requiredCourses;
    }

    private void saveCoursesSelection(Student student, List<Map.Entry<UUID, List<UUID>>> coursesBySemesters) {
        writeSelectedCourses(student, coursesBySemesters.stream().map(x -> Map.entry(x.getKey(), x.getValue())).toList());
    }

    private void writeSelectedCourses(Student student, List<Map.Entry<UUID, List<UUID>>> semesterIdWithCoursesIds) {
        var coursesToDelete = courseGateway
                .getSelectedCourses(student.getLogin());
        var coursesIdToDelete = coursesToDelete
                .stream()
                .map(SelectedCourses::getId)
                .toList();
        courseGateway.deleteSelectedAllById(coursesIdToDelete);

        for (var item : semesterIdWithCoursesIds) {
            var semesterId = item.getKey();
            var selectedCoursesIds = item.getValue();

            Semester semester = semesterGateway.getById(semesterId);

            courseGateway
                    .saveSelectedCourses(
                            selectedCoursesIds
                                    .stream()
                                    .map(x -> new SelectedCourses(student, semester, courseGateway.getById(x)))
                                    .toList()
                    );
        }
    }
}
