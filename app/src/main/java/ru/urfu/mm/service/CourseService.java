package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.controller.course.CreateModuleCourseDTO;
import ru.urfu.mm.controller.course.EditModuleCourseDTO;
import ru.urfu.mm.controller.course.CourseDTO;
import ru.urfu.mm.controller.course.CourseStatisticsDTO;
import ru.urfu.mm.entity.EducationalProgramToCoursesWithSemesters;
import ru.urfu.mm.entity.SelectedCourses;
import ru.urfu.mm.entity.Semester;
import ru.urfu.mm.entity.SpecialCourse;
import ru.urfu.mm.exceptions.CourseRequiredCriteriaException;
import ru.urfu.mm.repository.EducationalModuleRepository;
import ru.urfu.mm.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.repository.SelectedCoursesRepository;
import ru.urfu.mm.repository.SpecialCourseRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;
    private final SelectedCoursesRepository selectedCoursesRepository;
    private final SpecialCourseRepository specialCourseRepository;
    private final EducationalModuleRepository educationalModuleRepository;

    @Autowired
    public CourseService(
            EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository,
            SelectedCoursesRepository selectedCoursesRepository,
            SpecialCourseRepository specialCourseRepository,
            EducationalModuleRepository educationalModuleRepository) {
        this.educationalProgramToCoursesWithSemestersRepository = educationalProgramToCoursesWithSemestersRepository;
        this.selectedCoursesRepository = selectedCoursesRepository;
        this.specialCourseRepository = specialCourseRepository;
        this.educationalModuleRepository = educationalModuleRepository;
    }

    public List<CourseForEducationalProgram> getCoursesByEducationalProgramAndSemesters(UUID educationalProgramId, List<UUID> semestersIds) {
        var coursesInfos = educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> x.getEducationalProgram().getId() == educationalProgramId)
                .toList();
        var courseIdToCourseInfo =
                coursesInfos
                        .stream()
                        .collect(
                                Collectors
                                        .groupingBy(x -> x.getSpecialCourse().getId())
                        );
        var temp = new HashMap<UUID, List<EducationalProgramToCoursesWithSemesters>>();
        for (var key : courseIdToCourseInfo.keySet()) {
            var list = courseIdToCourseInfo.get(key)
                    .stream()
                    .filter(x -> containsSemester(x, semestersIds))
                    .toList();
            temp.put(key, list);
        }
        courseIdToCourseInfo = temp;
        var coursesForEducationalProgram = new ArrayList<CourseForEducationalProgram>();
        for (var courseId : courseIdToCourseInfo.keySet()) {
            var info = courseIdToCourseInfo.get(courseId);
            if ((info == null) || info.isEmpty()) {
                continue;
            }
            var first = info.getFirst();
            SpecialCourse firstCourseInfo = null;
            if(first != null) {
                firstCourseInfo = first.getSpecialCourse();
            }
            if(firstCourseInfo == null) {
                continue;
            }
            var isRequired = first.isRequiredCourse();
            UUID requiredSemesterId = null;
            if(isRequired) {
                requiredSemesterId = first.getSemester().getId();
            }
            var course = new CourseForEducationalProgram(
                    courseId,
                    firstCourseInfo.getName(),
                    firstCourseInfo.getCreditsCount(),
                    firstCourseInfo.getControl(),
                    firstCourseInfo.getDescription(),
                    info.stream().map(x -> new Semester(
                            x.getSemester().getId(),
                            x.getSemester().getYear(),
                            x.getSemester().getYear() // todo: фикси ошибку
                    )).toList(),
                    firstCourseInfo.getEducationalModule().getId(),
                    requiredSemesterId
            );
            coursesForEducationalProgram.add(course);
        }

        return coursesForEducationalProgram;
    }

    public Map<UUID, List<UUID>> getSelectedCoursesIds(UUID studentLogin, List<UUID> semestersIds) {
        var semesterIdsSet = new HashSet<>(semestersIds);
        var selectedCourses = selectedCoursesRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getLogin().equals(studentLogin))
                .toList();
        return selectedCourses
                .stream()
                .filter(x -> semesterIdsSet.contains(x.getSemester().getId()))
                .collect(Collectors.toMap(SelectedCourses::getId, x -> List.of(x.getSpecialCourse().getId())));
    }

    public List<Map.Entry<UUID, UUID>> getRequiredCoursesForEducationalProgram(UUID educationalProgramId) {
        var requiredCourses = educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> x.getEducationalProgram().getId() == educationalProgramId && x.isRequiredCourse())
                .toList()
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

    public List<CourseStatisticsDTO> getActualSpecialCoursesStatistics(List<UUID> semestersId) {
        var courses = educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> semestersId.contains(x.getSemester().getId()))
                .distinct()
                .toList();
        return courses
                .stream()
                .map(x -> new CourseStatisticsDTO(
                                x.getSpecialCourse().getId(),
                                x.getSpecialCourse().getName(),
                                specialCourseStudentsCount(x.getSpecialCourse().getId())
                        )
                )
                .toList();
    }

    private int specialCourseStudentsCount(UUID courseId) {
        var coursesModels = selectedCoursesRepository
                .findAll()
                .stream()
                .filter(x -> x.getSpecialCourse().getId().equals(courseId))
                .map(x -> x.getStudent().getLogin())
                .distinct()
                .toList();
        return coursesModels.size();
    }

    private boolean containsSemester(EducationalProgramToCoursesWithSemesters model, List<UUID> semestersIds)
    {
        return semestersIds == null || semestersIds.contains(model.getSemester().getId());
    }

    public List<CourseDTO> getEducationalModuleCourses(UUID educationalModuleId) {
        var courses = specialCourseRepository
                .findAll()
                .stream()
                .filter(x -> educationalModuleId.equals(x.getEducationalModule().getId()))
                .toList();

        return courses
                .stream()
                .map(ModelConverterHelper::toDomain)
                .toList();
    }

    public CourseDTO getCourse(UUID specialCourseId) {
        var course = specialCourseRepository
                .findById(specialCourseId)
                .get();
        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getCreditsCount(),
                course.getControl(),
                course.getDescription(),
                course.getEducationalModule().getId(),
                course.getTeacherName(),
                course.getDepartment()
        );
    }

    public void createModuleSpecialCourse(CreateModuleCourseDTO createModuleCourseDTO) {
        var educationalModule = educationalModuleRepository
                .findById(createModuleCourseDTO.moduleId())
                .get();
        var course = new SpecialCourse(
                createModuleCourseDTO.courseName(),
                createModuleCourseDTO.creditsCount(),
                createModuleCourseDTO.control(),
                createModuleCourseDTO.courseDescription(),
                createModuleCourseDTO.department(),
                createModuleCourseDTO.teacherName(),
                educationalModule
        );
        specialCourseRepository.save(course);
    }

    public void deleteCourse(UUID courseId) {
        specialCourseRepository.deleteById(courseId);
    }

    public void editModuleSpecialCourse(EditModuleCourseDTO editModuleCourseDTO) {
        var oldCourseValue = specialCourseRepository
                .findById(editModuleCourseDTO.courseId())
                .get();

        if (oldCourseValue != null) {
            oldCourseValue.setName(editModuleCourseDTO.courseName());
            oldCourseValue.setDepartment(editModuleCourseDTO.department());
            oldCourseValue.setTeacherName(editModuleCourseDTO.teacherName());
            oldCourseValue.setControl(editModuleCourseDTO.control());
            oldCourseValue.setCreditsCount(editModuleCourseDTO.creditsCount());
            oldCourseValue.setDescription(editModuleCourseDTO.courseDescription());

            specialCourseRepository.save(oldCourseValue);
        }
    }
}
