package ru.urfu.mm.application.usecase.get_base_syllabus;

import java.util.UUID;

public record CourseResponse(
        UUID id,
        String name,
        int semesterNumber
) {
}
