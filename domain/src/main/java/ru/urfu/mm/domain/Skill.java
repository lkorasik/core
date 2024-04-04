package ru.urfu.mm.domain;

/**
 * Навык.
 * Представляет собой навык
 */
public class Skill {
    /**
     * Название навыка
     */
    private final String name;
    /**
     * Уровень навыка
     */
    private final SkillLevel level;

    public Skill(String name, SkillLevel level) {
        this.name = name;
        this.level = level;
    }
}
