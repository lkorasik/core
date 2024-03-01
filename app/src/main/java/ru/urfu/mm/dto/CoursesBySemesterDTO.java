package ru.urfu.mm.dto;

import java.util.List;
import java.util.UUID;

public class CoursesBySemesterDTO {
    private UUID semesterId;
    private List<UUID> coursesIds;

    public CoursesBySemesterDTO(UUID semesterId, List<UUID> coursesIds) {
        this.semesterId = semesterId;
        this.coursesIds = coursesIds;
    }

    public UUID getSemesterId() {
        return semesterId;
    }

    public List<UUID> getCoursesIds() {
        return coursesIds;
    }
}
