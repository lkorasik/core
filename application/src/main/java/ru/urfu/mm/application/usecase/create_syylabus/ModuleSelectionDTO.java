package ru.urfu.mm.application.usecase.create_syylabus;

import java.util.List;
import java.util.UUID;

public record ModuleSelectionDTO(
        UUID moduleId,
        List<CourseSelectionDTO> courses
) { }
