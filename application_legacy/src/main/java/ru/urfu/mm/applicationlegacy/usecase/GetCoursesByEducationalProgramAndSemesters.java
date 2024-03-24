package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.CourseGateway;
import ru.urfu.mm.domainlegacy.EducationalProgramToCoursesWithSemesters;
import ru.urfu.mm.domainlegacy.Semester;
import ru.urfu.mm.domainlegacy.SpecialCourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GetCoursesByEducationalProgramAndSemesters {
    private final CourseGateway courseGateway;

    public GetCoursesByEducationalProgramAndSemesters(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
    }

    public List<CourseForEducationalProgram> getCoursesByEducationalProgramAndSemesters(UUID educationalProgramId, List<UUID> semestersIds) {
        var coursesInfos = courseGateway.getEducationalProgramToCoursesWithSemestersByEducationalProgram(educationalProgramId);
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

    private boolean containsSemester(EducationalProgramToCoursesWithSemesters model, List<UUID> semestersIds)
    {
        return semestersIds == null || semestersIds.contains(model.getSemester().getId());
    }
}
