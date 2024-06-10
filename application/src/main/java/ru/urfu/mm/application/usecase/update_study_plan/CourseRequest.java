package ru.urfu.mm.application.usecase.update_study_plan;

import java.util.UUID;

public record CourseRequest(UUID courseId, int semesterNumber) {}
