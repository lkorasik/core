package ru.urfu.mm.controller.program;

import java.util.UUID;

public class CourseDTO {
    private UUID id;
    private String name;

    public CourseDTO(UUID id, String name) {
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
