package ru.urfu.mm.dto;

import java.util.List;
import java.util.UUID;

public class ModuleCoursesDTO {
    private UUID moduleId;
    private List<RecommendedCourseDTO> courses;

    public ModuleCoursesDTO(){

    }

    public ModuleCoursesDTO(UUID moduleId, List<RecommendedCourseDTO> courses) {
        this.moduleId = moduleId;
        this.courses = courses;
    }

    public UUID getModuleId() {
        return moduleId;
    }

    public List<RecommendedCourseDTO> getCourses() {
        return courses;
    }
}
