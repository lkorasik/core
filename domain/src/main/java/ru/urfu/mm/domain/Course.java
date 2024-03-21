package ru.urfu.mm.domain;

/**
 * Учебный курс.
 * Представляет собой обязательный курс, спецкурс или НИР
 */
public class Course {
    /**
     * Название курса
     */
    private final String name;

    public Course(String name) {
        this.name = name;
    }
}
