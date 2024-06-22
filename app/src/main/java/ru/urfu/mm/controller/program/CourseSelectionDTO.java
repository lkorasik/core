package ru.urfu.mm.controller.program;

import java.util.UUID;

public record  CourseSelectionDTO(
        UUID courseId,
        UUID semesterId
) { }
