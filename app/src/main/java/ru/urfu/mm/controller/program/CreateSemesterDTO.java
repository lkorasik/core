package ru.urfu.mm.controller.program;

import java.util.List;
import java.util.UUID;

public class CreateSemesterDTO {
    public List<UUID> requiredCourses;
    public List<UUID> specialCourses;
    public List<UUID> scienceWorks;

    public CreateSemesterDTO(List<UUID> requiredCourses, List<UUID> specialCourses, List<UUID> scienceWorks) {
        this.requiredCourses = requiredCourses;
        this.specialCourses = specialCourses;
        this.scienceWorks = scienceWorks;
    }

    public List<UUID> getRequiredCourses() {
        return requiredCourses;
    }

    public List<UUID> getSpecialCourses() {
        return specialCourses;
    }

    public List<UUID> getScienceWorks() {
        return scienceWorks;
    }
}
