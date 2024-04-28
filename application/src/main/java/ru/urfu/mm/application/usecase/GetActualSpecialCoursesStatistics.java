package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.domain.ProgramToCoursesWithSemesters;

import java.util.List;
import java.util.UUID;

public class GetActualSpecialCoursesStatistics {
    private final CourseGateway courseGateway;

    public GetActualSpecialCoursesStatistics(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
    }

    public List<ProgramToCoursesWithSemesters> getActualSpecialCoursesStatistics(List<UUID> semestersId) {
        return courseGateway.getEducationalProgramToCoursesWithSemestersBySemesters(semestersId);
    }
}
