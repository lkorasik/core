package ru.urfu.mm.domain;

import java.util.*;

/**
 * Образовательный модуль
 */
public class EducationalModule {
    /**
     * Идентификатор модуля
     */
    private final UUID id;
    /**
     * Название модуля
     */
    private final String name;
    /**
     * Список курсов
     */
    private final List<Course> courses;

    public EducationalModule(UUID id, String name) {
        this.name = name;
        this.id = id;
        this.courses = new ArrayList<>();
    }

    public EducationalModule(UUID id, String name, List<Course> courses) {
        this.id = id;
        this.name = name;
        this.courses = courses;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }
}
