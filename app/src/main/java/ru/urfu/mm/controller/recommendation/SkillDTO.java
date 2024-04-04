package ru.urfu.mm.controller.recommendation;

import ru.urfu.mm.entity.SkillLevel;

import java.util.UUID;

public record SkillDTO(
        UUID id,
        String name,
        SkillLevel level
) { }
