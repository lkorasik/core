package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Учебный курс.
 * Представляет собой обязательный курс, спецкурс или НИР.
 */
public class Course {
    /**
     * Название курса
     */
    private final String name;
    /**
     * Навыки необходимые для прохождения данного курса
     */
    private final List<Skill> requiredSkills;
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

    public Course(String name, String description, String department, String teacher) {
        this.name = name;
        this.description = description;
        this.department = department;
        this.teacher = teacher;
        this.requiredSkills = new ArrayList<>();
    }
}
