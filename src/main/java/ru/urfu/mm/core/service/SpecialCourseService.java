package ru.urfu.mm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.entity.EducationalProgramToCoursesWithSemesters;
import ru.urfu.mm.core.entity.Semester;
import ru.urfu.mm.core.entity.SpecialCourse;
import ru.urfu.mm.core.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.core.repository.SelectedCoursesRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpecialCourseService {
    @Autowired
    private EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;
    @Autowired
    private SelectedCoursesRepository selectedCoursesRepository;

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
            if (info != null) {
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

    private boolean containsSemester(EducationalProgramToCoursesWithSemesters model, List<UUID> semestersIds)
    {
        return semestersIds == null || semestersIds.contains(model.getSemester().getId());
    }
}
