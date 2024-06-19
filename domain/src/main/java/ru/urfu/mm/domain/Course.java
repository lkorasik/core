package ru.urfu.mm.domain;

import ru.urfu.mm.domain.enums.ControlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Учебный курс
 */
public class Course {
    /**
     * Идентификатор курса
     */
    private final UUID id;
    /**
     * Назавние курса
     */
    private final String name;
    /**
     * Число зачетных единиц
     */
    private final int credits;
    /**
     * Формат итогового контроля
     */
    private final ControlTypes control;
    /**
     * Описание курса
     */
    private final String description;
    /**
     * Кафедра
     */
    private final String department;
    /**
     * Преподаватель
     */
    private final String teacher;
    /**
     * Список требуемых навыков
     */
    private final List<Skill> requiredSkills;

    public Course(UUID id, String name, int credits, ControlTypes control, String description, String department, String teacher) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.control = control;
        this.description = description;
        this.department = department;
        this.teacher = teacher;
        this.requiredSkills = new ArrayList<>();
    }
}
