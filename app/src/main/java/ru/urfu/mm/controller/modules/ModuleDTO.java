package ru.urfu.mm.controller.modules;

import java.util.UUID;

public record ModuleDTO(
        UUID id,
        String name
) { }
