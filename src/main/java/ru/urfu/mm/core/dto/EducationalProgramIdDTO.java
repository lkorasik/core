package ru.urfu.mm.core.dto;

import java.util.UUID;

public class EducationalProgramIdDTO {
    private UUID id;

    public EducationalProgramIdDTO(UUID id) {
        this.id = id;
    }

    public EducationalProgramIdDTO(){

    }

    public UUID getId() {
        return id;
    }
}
