package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.dto.CreateModuleSpecialCourseDTO;
import ru.urfu.mm.dto.EditModuleSpecialCourseDTO;
import ru.urfu.mm.dto.SpecialCourseDTO;
import ru.urfu.mm.dto.SpecialCourseStatisticsDTO;
import ru.urfu.mm.entity.EducationalProgramToCoursesWithSemesters;
import ru.urfu.mm.entity.Semester;
import ru.urfu.mm.entity.SpecialCourse;
import ru.urfu.mm.repository.EducationalModuleRepository;
import ru.urfu.mm.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.repository.SelectedCoursesRepository;
import ru.urfu.mm.repository.SpecialCourseRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpecialCourseService {
    @Autowired
    private EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;
    @Autowired
    private SelectedCoursesRepository selectedCoursesRepository;
    @Autowired
    private SpecialCourseRepository specialCourseRepository;
    @Autowired
    private EducationalModuleRepository educationalModuleRepository;

    public ArrayList<CourseForEducationalProgram> getCoursesByEducationalProgramAndSemesters(UUID educationalProgramId, List<UUID> semestersIds) {
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
        var semesterIdsSet = new HashSet<UUID>(semestersIds);
        var selectedCourses = selectedCoursesRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getLogin().equals(studentLogin))
                .toList();
        return selectedCourses
                .stream()
                .filter(x -> semesterIdsSet.contains(x.getSemester().getId()))
                .collect(Collectors.toMap(x -> x.getId(), x -> List.of(x.getSpecialCourse().getId())));
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
                .map(x -> x.getKey())
                .collect(Collectors.toSet());
        if(requiredCourses.size() != coursesIdsHashSet.size()) {
            throw new RuntimeException("There is more than one semester, in which course is required");
        }
        return requiredCourses;
    }

    public List<SpecialCourseStatisticsDTO> getActualSpecialCoursesStatistics(List<UUID> semestersId) {
        var courses = educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> semestersId.contains(x.getSemester().getId()))
                .distinct()
                .toList();
        return courses
                .stream()
                .map(x -> new SpecialCourseStatisticsDTO(
                        x.getSpecialCourse().getId(),
                        x.getSpecialCourse().getName(),
                        specialCourseStudentsCount(x.getSpecialCourse().getId())
                        ))
                .toList();
    }

    public List<SpecialCourseDTO> getAllCourses() {
        var courses = specialCourseRepository
                .findAll()
                .stream()
                .toList();
        return courses
                .stream()
                .map(ModelConverterHelper::toDomain)
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

    public List<SpecialCourseDTO> getEducationalModuleCourses(UUID educationalModuleId) {
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

    public SpecialCourseDTO getCourse(UUID specialCourseId) {
        var course = specialCourseRepository
                .findById(specialCourseId)
                .get();
        return new SpecialCourseDTO(
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

    public void createModuleSpecialCourse(CreateModuleSpecialCourseDTO createModuleSpecialCourseDTO) {
        var educationalModule = educationalModuleRepository
                .findById(createModuleSpecialCourseDTO.getEducationalModuleId())
                .get();
        var course = new SpecialCourse(
                createModuleSpecialCourseDTO.getCourseName(),
                createModuleSpecialCourseDTO.getCreditsCount(),
                createModuleSpecialCourseDTO.getControl(),
                createModuleSpecialCourseDTO.getCourseDescription(),
                createModuleSpecialCourseDTO.getDepartment(),
                createModuleSpecialCourseDTO.getTeacherName(),
                educationalModule
        );
        specialCourseRepository.save(course);
    }

    public void deleteCourse(UUID courseId) {
        specialCourseRepository.deleteById(courseId);
    }

    public void editModuleSpecialCourse(EditModuleSpecialCourseDTO editModuleSpecialCourseDTO) {
        var oldCourseValue = specialCourseRepository
                .findById(editModuleSpecialCourseDTO.getSpecialCourseId())
                .get();

        if (oldCourseValue != null) {
            oldCourseValue.setName(editModuleSpecialCourseDTO.getCourseName());
            oldCourseValue.setDepartment(editModuleSpecialCourseDTO.getDepartment());
            oldCourseValue.setTeacherName(editModuleSpecialCourseDTO.getTeacherName());
            oldCourseValue.setControl(editModuleSpecialCourseDTO.getControl());
            oldCourseValue.setCreditsCount(editModuleSpecialCourseDTO.getCreditsCount());
            oldCourseValue.setDescription(editModuleSpecialCourseDTO.getCourseDescription());

            specialCourseRepository.save(oldCourseValue);
        }
    }
}
