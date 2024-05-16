package ru.urfu.mm.domain;

import ru.urfu.mm.domain.enums.SkillLevel;

import java.util.UUID;

/**
 * Навык
 */
public class Skill {
    /**
     * Идентификатор навыка
     */
    private UUID id;
    /**
     * Назавние навыка
     */
    private String name;
    /**
     * Уровень навыка
     */
    private SkillLevel level;

    public Skill(UUID id, String name, SkillLevel level) {
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
}
