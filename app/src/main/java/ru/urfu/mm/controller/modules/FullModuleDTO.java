package ru.urfu.mm.controller.modules;

import java.util.List;
import java.util.UUID;

public record FullModuleDTO(
        UUID id,
        String name,
        List<FullCourseDTO> courses
) { }
