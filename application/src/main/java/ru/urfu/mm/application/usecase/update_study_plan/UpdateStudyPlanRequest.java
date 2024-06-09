package ru.urfu.mm.application.usecase.update_study_plan;

import java.util.List;
import java.util.UUID;

public record UpdateStudyPlanRequest(
        UUID programId,
        int startYear,
        List<CourseRequest> courses
) {}
