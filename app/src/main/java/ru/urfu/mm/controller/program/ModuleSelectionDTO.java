package ru.urfu.mm.controller.program;

import java.util.List;
import java.util.UUID;

public record ModuleSelectionDTO(
        UUID moduleId,
        List<CourseSelectionDTO> courses
) { }