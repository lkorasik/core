package ru.urfu.mm.dto;

import java.util.List;
import java.util.UUID;

public class FullSemesterDTO {
    private UUID id;
    private List<CourseDTO> requiredCourses;
    private List<CourseDTO> specialCourses;
    private List<CourseDTO> scienceWorks;

    public FullSemesterDTO(UUID id, List<CourseDTO> requiredCourses, List<CourseDTO> specialCourses, List<CourseDTO> scienceWorks) {
        this.id = id;
        this.requiredCourses = requiredCourses;
        this.specialCourses = specialCourses;
        this.scienceWorks = scienceWorks;
    }

    public UUID getId() {
        return id;
    }

    public List<CourseDTO> getRequiredCourses() {
        return requiredCourses;
    }

    public List<CourseDTO> getSpecialCourses() {
        return specialCourses;
    }

    public List<CourseDTO> getScienceWorks() {
        return scienceWorks;
    }
}
