package ru.urfu.mm.controller.modules;

import java.util.List;
import java.util.UUID;

public record CreateModuleDTO(
        String moduleName,
        List<UUID> coursesIds
) { }
