package ru.urfu.mm.controller.program;

import java.util.List;
import java.util.UUID;

public record SyllabusDTO(
        UUID programId,
        UUID firstSemesterId,
        List<ModuleSelectionDTO> modules
) { }
