package ru.urfu.mm.dto;

import ru.urfu.mm.entity.Control;

import java.util.Objects;
import java.util.UUID;

public class SpecialCourseDTO {
    private UUID id;
    private String name;
    private int creditsCount;
    private Control control;
    private String description;
    private UUID educationalModuleId;
    private String teacherName;
    private String department;

    public SpecialCourseDTO(UUID id, String name, int creditsCount, Control control, String description, UUID educationalModuleId, String teacherName, String department) {
        this.id = id;
        this.name = name;
        this.creditsCount = creditsCount;
        this.control = control;
        this.description = description;
        this.educationalModuleId = educationalModuleId;
        this.teacherName = teacherName;
        this.department = department;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCreditsCount() {
        return creditsCount;
    }

    public Control getControl() {
        return control;
    }

    public String getDescription() {
        return description;
    }

    public UUID getEducationalModuleId() {
        return educationalModuleId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SpecialCourseDTO that = (SpecialCourseDTO) o;
        return creditsCount == that.creditsCount
               && Objects.equals(id, that.id)
               && Objects.equals(name, that.name)
               && control == that.control
               && Objects.equals(description, that.description)
               && Objects.equals(educationalModuleId, that.educationalModuleId)
               && Objects.equals(teacherName, that.teacherName)
               && Objects.equals(department, that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creditsCount, control, description, educationalModuleId, teacherName, department);
    }
}
