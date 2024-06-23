package ru.urfu.mm.application.usecase.create_syylabus;

import java.util.List;
import java.util.UUID;

public record CreateSyllabusRequest(
        UUID programId,
        UUID firstSemesterId,
        List<ModuleSelectionDTO> modules
) { }
