package ru.urfu.mm.domain;

import java.util.List;

/**
 * Модуль
 * Представляет собой образовательный модуль
 */
public class Module {
    /**
     * Список курсов
     */
    private final List<Course> courses;
    /**
     * Название модуля
     */
    private final String name;

    public Module(List<Course> courses, String name) {
        this.courses = courses;
        this.name = name;
    }
}
