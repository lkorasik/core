package ru.urfu.mm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.dto.CoursesBySemesterDTO;
import ru.urfu.mm.core.entity.SelectedCourses;
import ru.urfu.mm.core.entity.Semester;
import ru.urfu.mm.core.entity.SpecialCourse;
import ru.urfu.mm.core.entity.Student;
import ru.urfu.mm.core.exceptions.CoursesSelectionValidationException;
import ru.urfu.mm.core.repository.SelectedCoursesRepository;
import ru.urfu.mm.core.repository.SemesterRepository;
import ru.urfu.mm.core.repository.SpecialCourseRepository;
import ru.urfu.mm.core.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CoursesSelectionService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SpecialCourseService specialCourseService;
    @Autowired
    private SelectedCoursesRepository selectedCoursesRepository;
    @Autowired
    private SemesterRepository semesterRepository;
    @Autowired
    private SpecialCourseRepository specialCourseRepository;

    private boolean isValidForEducationalProgram(UUID studentId, UUID semesterId, List<UUID> selectedCoursesIds) {
        var selectedCoursesIdsSet = new HashSet<>(selectedCoursesIds);

        var student = studentRepository.getReferenceById(studentId);
        var coursesIdsForEducationalProgram = specialCourseService
                .getCoursesByEducationalProgramAndSemesters(student.getEducationalProgram().getId(), List.of(semesterId))
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toSet());
        var requiredCoursesIds = specialCourseService
                .getRequiredCoursesForEducationalProgram(student.getEducationalProgram().getId())
                .stream()
                .filter(x -> x.getValue() == semesterId)
                .map(x -> x.getKey())
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

    private CourseSelectionValidationStatus validateCoursesSelectionBySemester(UUID studentId, UUID semesterId, List<UUID> selectedCoursesIds) {
        if(!isValidForEducationalProgram(studentId, semesterId, selectedCoursesIds)) {
            return CourseSelectionValidationStatus.MISSING_REQUIRED_COURSES_EXTRA_COURSES_FOUND;
        }
        return CourseSelectionValidationStatus.OK;
    }

    private boolean validateModules(UUID studentId, List<UUID> selectedCoursesIds) {
        var selectedCoursesIdsSet = new HashSet<>(selectedCoursesIds);

        var student = studentRepository.getReferenceById(studentId);
        var courses = specialCourseService
                .getCoursesByEducationalProgramAndSemesters(student.getEducationalProgram().getId(), null)
                .stream()
                .map(x -> Map.entry(x.getEducationalModuleId(), x.getId()))
                .toList();
        for (var educationalModuleIdWithCoursesIds : courses) {
            if(educationalModuleIdWithCoursesIds.getKey() == null) {
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

    public void validateCoursesSelection(UUID studentId, List<CoursesBySemesterDTO> coursesBySemesters) {
        var semesterIdToValidationStatus = new HashMap<UUID, CourseSelectionValidationStatus>();
        for(var coursesBySemester : coursesBySemesters) {
            var validationResult = validateCoursesSelectionBySemester(studentId, coursesBySemester.getSemesterId(), coursesBySemester.getCoursesIds());

            if(validationResult != CourseSelectionValidationStatus.OK) {
                semesterIdToValidationStatus.put(coursesBySemester.getSemesterId(), validationResult);
            }
        }
        if(!semesterIdToValidationStatus.isEmpty()) {
            throw new CoursesSelectionValidationException(true);
        }

        var isValidForModules = validateModules(
                studentId,
                coursesBySemesters
                        .stream()
                        .map(CoursesBySemesterDTO::getCoursesIds)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
        );
        if(!isValidForModules) {
            throw new CoursesSelectionValidationException(true);
        }
    }

    public void saveCoursesSelection(UUID studentId, List<CoursesBySemesterDTO> coursesBySemesters) {
        writeSelectedCourses(studentId, coursesBySemesters.stream().map(x -> Map.entry(x.getSemesterId(), x.getCoursesIds())).toList());
    }

    public void writeSelectedCourses(UUID studentId, List<Map.Entry<UUID, List<UUID>>> semesterIdWithCoursesIds) {
        var coursesToDelete = selectedCoursesRepository
                .findAll()
                .stream()
                .filter(x -> x.getId().equals(studentId))
                .toList();
        var coursesIdToDelete = coursesToDelete
                .stream()
                .map(SelectedCourses::getId)
                .toList();
        selectedCoursesRepository.deleteAllById(coursesIdToDelete);

        for(var item : semesterIdWithCoursesIds) {
            var semesterId = item.getKey();
            var selectedCoursesIds = item.getValue();

            Student student = studentRepository.getReferenceById(studentId);
            Semester semester = semesterRepository.getReferenceById(semesterId);

            selectedCoursesRepository
                    .saveAll(selectedCoursesIds
                            .stream()
                            .map(x -> new SelectedCourses(student, semester, specialCourseRepository.getReferenceById(x)))
                            .toList()
                    );
        }
    }
}
