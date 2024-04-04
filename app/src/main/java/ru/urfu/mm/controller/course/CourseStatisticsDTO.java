package ru.urfu.mm.controller.course;

import java.util.Objects;
import java.util.UUID;

public record CourseStatisticsDTO(
        UUID id,
        String name,
        int studentsCount) {
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CourseStatisticsDTO that = (CourseStatisticsDTO) o;
        return studentsCount == that.studentsCount
               && Objects.equals(id, that.id)
               && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, studentsCount);
    }
}
