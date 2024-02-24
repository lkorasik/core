package ru.urfu.mm.core.dto;

import ru.urfu.mm.core.entity.Control;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RecommendedCourseDTO {
    private UUID id;
    private String name;
    private int credits;
    private Control control;
    private String description;
    private List<SemesterDTO> semesters;
    private UUID educationalModuleId;
    /**
     * Если курс является обязательным, то поле заполняется id семестра,
     * в котором этот курс будет обязательным, иначе - null.
     */
    private Optional<UUID> requiredSemesterId;
    private List<SkillDTO> requiredSkills;
    private List<SkillDTO> resultSkills;

    public RecommendedCourseDTO(UUID id, String name, int credits, Control control, String description, List<SemesterDTO> semesters, UUID educationalModuleId, Optional<UUID> requiredSemesterId, List<SkillDTO> requiredSkills, List<SkillDTO> resultSkills) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.control = control;
        this.description = description;
        this.semesters = semesters;
        this.educationalModuleId = educationalModuleId;
        this.requiredSemesterId = requiredSemesterId;
        this.requiredSkills = requiredSkills;
        this.resultSkills = resultSkills;
    }

    public RecommendedCourseDTO() {

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public Control getControl() {
        return control;
    }

    public String getDescription() {
        return description;
    }

    public List<SemesterDTO> getSemesters() {
        return semesters;
    }

    public UUID getEducationalModuleId() {
        return educationalModuleId;
    }

    public Optional<UUID> getRequiredSemesterId() {
        return requiredSemesterId;
    }

    public List<SkillDTO> getRequiredSkills() {
        return requiredSkills;
    }

    public List<SkillDTO> getResultSkills() {
        return resultSkills;
    }
}
