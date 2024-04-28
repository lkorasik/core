package ru.urfu.mm.controller.skill;

import ru.urfu.mm.persistance.entity.SkillLevel;

import java.util.UUID;

public record SkillDTO(
        UUID id,
        String name,
        SkillLevel level
) { }
