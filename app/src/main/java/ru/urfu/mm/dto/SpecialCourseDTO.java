package ru.urfu.mm.dto;

import ru.urfu.mm.entity.Control;

import java.util.UUID;

public class SpecialCourseDTO {
    private UUID id;
    private String name;
    private int creditsCount;
    private Control control;
    private String description;
    private UUID educationalModuleId;
    private String teacherName;
    private String department;

    public SpecialCourseDTO(UUID id, String name, int creditsCount, Control control, String description, UUID educationalModuleId, String teacherName, String department) {
        this.id = id;
        this.name = name;
        this.creditsCount = creditsCount;
        this.control = control;
        this.description = description;
        this.educationalModuleId = educationalModuleId;
        this.teacherName = teacherName;
        this.department = department;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCreditsCount() {
        return creditsCount;
    }

    public Control getControl() {
        return control;
    }

    public String getDescription() {
        return description;
    }

    public UUID getEducationalModuleId() {
        return educationalModuleId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getDepartment() {
        return department;
    }
}
