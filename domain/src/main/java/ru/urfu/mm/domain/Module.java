package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Образовательный модуль
 */
public class Module {
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

    public Module(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Module module = (Module) obj;
        return Objects.equals(id, module.id) && Objects.equals(name, module.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
