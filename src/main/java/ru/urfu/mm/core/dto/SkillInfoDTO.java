package ru.urfu.mm.core.dto;

import java.util.UUID;

public class SkillInfoDTO {
    private UUID id;
    private String name;

    public SkillInfoDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
