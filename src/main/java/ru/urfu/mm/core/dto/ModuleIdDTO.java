package ru.urfu.mm.core.dto;

import java.util.UUID;

public class ModuleIdDTO {
    private UUID educationalModuleId;

    public ModuleIdDTO() {

    }

    public ModuleIdDTO(UUID educationalModuleId) {
        this.educationalModuleId = educationalModuleId;
    }

    public UUID getEducationalModuleId() {
        return educationalModuleId;
    }
}
