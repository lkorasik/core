package ru.urfu.mm.application.usecase.get_editable_syllabus;

import java.util.UUID;

public record GetCourseSelectionDTO(
        UUID courseId,
        UUID semesterId
) { }
