package ru.urfu.mm.domain;

import ru.urfu.mm.domain.enums.ControlTypes;

import java.util.Objects;
import java.util.UUID;

/**
 * Учебныц курс.
 */
public class Course {
    private UUID id;
    private String name;
    private int credits;
    private ControlTypes controlTypes;
    private String description;
    private String department;
    private String teacher;
    @Deprecated
    private Module module;

    public Course(
            UUID id,
            String name,
            int credits,
            ControlTypes controlTypes,
            String description,
            String department,
            String teacher,
            Module module) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.controlTypes = controlTypes;
        this.description = description;
        this.department = department;
        this.teacher = teacher;
        this.module = module;
    }

    public Course(
            String name,
            int credits,
            ControlTypes controlTypes,
            String description,
            String department,
            String teacher,
            Module module) {
        this.name = name;
        this.credits = credits;
        this.controlTypes = controlTypes;
        this.description = description;
        this.department = department;
        this.teacher = teacher;
        this.module = module;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public ControlTypes getControl() {
        return controlTypes;
    }

    public String getDescription() {
        return description;
    }

    public String getDepartment() {
        return department;
    }

    public String getTeacher() {
        return teacher;
    }

    public Module getEducationalModule() {
        return module;
    }

    public void setEducationalModule(Module module) {
        this.module = module;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setControl(ControlTypes controlTypes) {
        this.controlTypes = controlTypes;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Course that = (Course) obj;
        return credits == that.credits && Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
               controlTypes == that.controlTypes && Objects.equals(description, that.description) &&
               Objects.equals(department, that.department) && Objects.equals(teacher, that.teacher) &&
               Objects.equals(module, that.module);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, credits, controlTypes, description, department, teacher, module);
    }
}
