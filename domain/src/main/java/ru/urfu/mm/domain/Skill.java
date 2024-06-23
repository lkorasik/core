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
    private final UUID id;
    /**
     * Назавние навыка
     */
    private final String name;
    /**
     * Уровень навыка
     */
    private final SkillLevel level;

    public Skill(UUID id, String name, SkillLevel level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }
}
