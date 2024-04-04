package ru.urfu.mm.controller.modules;

import java.util.List;
import java.util.UUID;

public record GetModulesDTO(
        List<UUID> modulesIds
) { }
