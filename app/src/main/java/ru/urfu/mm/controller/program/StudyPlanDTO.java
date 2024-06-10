package ru.urfu.mm.controller.program;

import java.util.List;
import java.util.UUID;

public record StudyPlanDTO(
        int startYear,
        UUID programId,
        List<ModuleSelectionDTO> modules
) { }
