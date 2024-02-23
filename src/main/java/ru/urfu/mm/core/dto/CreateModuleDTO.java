package ru.urfu.mm.core.dto;

import java.util.List;
import java.util.UUID;

public class CreateModuleDTO {
    private final String educationalModuleName;
    private final List<UUID> specialCoursesIds;

    public CreateModuleDTO(String educationalModuleName, List<UUID> specialCoursesIds) {
        this.educationalModuleName = educationalModuleName;
        this.specialCoursesIds = specialCoursesIds;
    }

    public String getEducationalModuleName() {
        return educationalModuleName;
    }

    public List<UUID> getSpecialCoursesIds() {
        return specialCoursesIds;
    }
}
