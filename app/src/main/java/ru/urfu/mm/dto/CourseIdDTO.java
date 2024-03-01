package ru.urfu.mm.dto;

import java.util.UUID;

public class CourseIdDTO {
    private UUID specialCourseId;

    public CourseIdDTO(UUID specialCourseId) {
        this.specialCourseId = specialCourseId;
    }

    public CourseIdDTO() {
    }

    public UUID getSpecialCourseId() {
        return specialCourseId;
    }
}
