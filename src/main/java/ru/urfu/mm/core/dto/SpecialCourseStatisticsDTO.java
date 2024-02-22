package ru.urfu.mm.core.dto;

import java.util.UUID;

public class SpecialCourseStatisticsDTO {
    private UUID id;
    private String name;
    private int studentsCount;

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
}
