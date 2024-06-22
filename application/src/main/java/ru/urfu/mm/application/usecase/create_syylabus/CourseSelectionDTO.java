package ru.urfu.mm.application.usecase.create_syylabus;

import java.util.UUID;

public record CourseSelectionDTO(
        UUID courseId,
        UUID semesterId
) { }
