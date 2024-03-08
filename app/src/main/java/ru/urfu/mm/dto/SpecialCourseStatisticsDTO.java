package ru.urfu.mm.dto;

import java.util.Objects;
import java.util.UUID;

public class SpecialCourseStatisticsDTO {
    private final UUID id;
    private final String name;
    private final int studentsCount;

    public SpecialCourseStatisticsDTO(UUID id, String name, int studentCount) {
        this.id = id;
        this.name = name;
        this.studentsCount = studentCount;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SpecialCourseStatisticsDTO that = (SpecialCourseStatisticsDTO) o;
        return studentsCount == that.studentsCount
               && Objects.equals(id, that.id)
               && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, studentsCount);
    }
}
