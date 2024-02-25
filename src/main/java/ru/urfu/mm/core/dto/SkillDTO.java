package ru.urfu.mm.core.dto;

import ru.urfu.mm.core.entity.SkillLevel;

import java.util.UUID;

public class SkillDTO {
    private UUID id;
    private String name;
    private SkillLevel level;

    public SkillDTO(UUID id, String name, SkillLevel level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SkillLevel getLevel() {
        return level;
    }
}
