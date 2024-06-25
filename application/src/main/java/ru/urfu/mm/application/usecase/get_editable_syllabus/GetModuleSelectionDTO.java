package ru.urfu.mm.application.usecase.get_editable_syllabus;

import java.util.List;
import java.util.UUID;

public record GetModuleSelectionDTO(
        UUID moduleId,
        List<GetCourseSelectionDTO> courses
) { }
