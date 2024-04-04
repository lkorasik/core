package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.SemesterGateway;
import ru.urfu.mm.domain.Semester;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class GetActualSemesters {
    private final SemesterGateway semesterGateway;

    public GetActualSemesters(SemesterGateway semesterGateway) {
        this.semesterGateway = semesterGateway;
    }

    public List<Semester> getActualSemesters() {
        ZonedDateTime now = Instant.now().atZone(ZoneId.systemDefault());
        return semesterGateway.GetLaterOrEqual(GetAcademicYear(now));
    }

    private static int GetAcademicYear(ZonedDateTime now)
    {
        var academicYear = now.getYear();
        if (now.getMonth().ordinal() < 9)
        {
            academicYear--;
        }

        return academicYear;
    }
}
