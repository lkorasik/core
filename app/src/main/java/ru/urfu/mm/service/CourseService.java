package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.controller.course.CourseStatisticsDTO;
import ru.urfu.mm.exceptions.CourseRequiredCriteriaException;
import ru.urfu.mm.persistance.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.persistance.repository.SelectedCoursesRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;
    private final SelectedCoursesRepository selectedCoursesRepository;

    @Autowired
    public CourseService(
            EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository,
            SelectedCoursesRepository selectedCoursesRepository) {
        this.educationalProgramToCoursesWithSemestersRepository = educationalProgramToCoursesWithSemestersRepository;
        this.selectedCoursesRepository = selectedCoursesRepository;
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
}
