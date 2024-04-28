package ru.urfu.mm.controller.program;

import java.util.List;

public record StudyPlanDTO(
        int startYear,
        List<ModuleSelectionDTO> modules
) { }
