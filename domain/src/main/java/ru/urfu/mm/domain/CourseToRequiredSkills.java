package ru.urfu.mm.domain;

import java.util.UUID;

/**
 * Курс и соотвествующий набор скиллов
 */
public class CourseToRequiredSkills {
    private UUID id;
    private SkillLevel skillLevel;
    private SpecialCourse specialCourse;
    private Skill requiredSkills;


    public CourseToRequiredSkills() {
    }

    public CourseToRequiredSkills(UUID id, SkillLevel skillLevel, SpecialCourse specialCourse, Skill requiredSkills) {
        this.id = id;
        this.skillLevel = skillLevel;
        this.specialCourse = specialCourse;
        this.requiredSkills = requiredSkills;
    }

    public CourseToRequiredSkills(SpecialCourse specialCourse, Skill requiredSkills, SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
        this.specialCourse = specialCourse;
        this.requiredSkills = requiredSkills;
    }

    public Skill getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(Skill requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public SpecialCourse getSpecialCourse() {
        return specialCourse;
    }

    public void setSpecialCourse(SpecialCourse specialCourse) {
        this.specialCourse = specialCourse;
    }

    public UUID getId() {
        return id;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }
}
