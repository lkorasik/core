package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    public EducationalModule(String name, UUID id) {
        this.name = name;
        this.id = id;
        this.courses = new ArrayList<>();
    }
}
