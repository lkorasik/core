package ru.urfu.mm.controller.recommendation;

import ru.urfu.mm.persistance.entity.SkillLevel;

import java.util.UUID;

public record SkillDTO(
        UUID id,
        String name,
        SkillLevel level
) { }
