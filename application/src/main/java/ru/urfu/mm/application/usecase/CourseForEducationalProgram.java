package ru.urfu.mm.application.usecase;

import ru.urfu.mm.domain.Control;
import ru.urfu.mm.domain.Semester;

import java.util.List;
import java.util.UUID;

public class CourseForEducationalProgram {
    private UUID id;
    private String name;
    private int creditsCount;
    private Control control;
    private String description;
    private List<Semester> semesters;
    private UUID educationalModuleId;

    /// <summary>
    ///     Если курс является обязательным, то поле заполняется id семестра,
    ///     в котором этот курс будет обязательным, иначе - null.
    /// </summary>
    public UUID requiredSemesterId;

    public CourseForEducationalProgram(UUID id, String name, int creditsCount, Control control, String description, List<Semester> semesters, UUID educationalModuleId, UUID requiredSemesterId) {
        this.id = id;
        this.name = name;
        this.creditsCount = creditsCount;
        this.control = control;
        this.description = description;
        this.semesters = semesters;
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

    public List<Semester> getSemesters() {
        return semesters;
    }

    public UUID getEducationalModuleId() {
        return educationalModuleId;
    }

    public UUID getRequiredSemesterId() {
        return requiredSemesterId;
    }
}