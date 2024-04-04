package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.CourseGateway;
import ru.urfu.mm.domainlegacy.EducationalProgramToCoursesWithSemesters;

import java.util.List;
import java.util.UUID;

public class GetActualSpecialCoursesStatistics {
    private final CourseGateway courseGateway;

    public GetActualSpecialCoursesStatistics(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
    }

    public List<EducationalProgramToCoursesWithSemesters> getActualSpecialCoursesStatistics(List<UUID> semestersId) {
        return courseGateway.getEducationalProgramToCoursesWithSemestersBySemesters(semestersId);
    }
}
