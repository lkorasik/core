package ru.urfu.mm.service;

import ru.urfu.mm.persistance.entity.SemesterEntity;
import ru.urfu.mm.persistance.entity.enums.Control;

import java.util.List;
import java.util.UUID;

public class CourseForEducationalProgram {
    private UUID id;
    private String name;
    private int creditsCount;
    private Control control;
    private String description;
    private List<SemesterEntity> semesterEntities;
    private UUID educationalModuleId;

    /// <summary>
    ///     Если курс является обязательным, то поле заполняется programId семестра,
    ///     в котором этот курс будет обязательным, иначе - null.
    /// </summary>
    public UUID requiredSemesterId;

    public CourseForEducationalProgram(UUID id, String name, int creditsCount, Control control, String description, List<SemesterEntity> semesterEntities, UUID educationalModuleId, UUID requiredSemesterId) {
        this.id = id;
        this.name = name;
        this.creditsCount = creditsCount;
        this.control = control;
        this.description = description;
        this.semesterEntities = semesterEntities;
        this.educationalModuleId = educationalModuleId;
        this.requiredSemesterId = requiredSemesterId;
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

    public List<SemesterEntity> getSemesters() {
        return semesterEntities;
    }

    public UUID getEducationalModuleId() {
        return educationalModuleId;
    }

    public UUID getRequiredSemesterId() {
        return requiredSemesterId;
    }
}
