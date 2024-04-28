package ru.urfu.mm.controller.course;

import ru.urfu.mm.persistance.entity.Control;

import java.util.Objects;
import java.util.UUID;

public record CourseDTO(
        UUID id,
        String name,
        int creditsCount,
        Control control,
        String description,
        UUID moduleId,
        String teacherName,
        String department
) {

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CourseDTO that = (CourseDTO) o;
        return creditsCount == that.creditsCount
               && Objects.equals(id, that.id)
               && Objects.equals(name, that.name)
               && control == that.control
               && Objects.equals(description, that.description)
               && Objects.equals(moduleId, that.moduleId)
               && Objects.equals(teacherName, that.teacherName)
               && Objects.equals(department, that.department);
    }

}
