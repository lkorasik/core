package ru.urfu.mm.domain;

import ru.urfu.mm.domain.enums.SkillLevel;

import java.util.UUID;

/**
 * Курс и соотвествующий набор скиллов
 */
@Deprecated
public class CourseToRequiredSkills {
    private UUID id;
    private SkillLevel skillLevel;
    private Course specialCourse;
    private Skill requiredSkills;

    public CourseToRequiredSkills(UUID id, SkillLevel skillLevel, Course specialCourse, Skill requiredSkills) {
        this.id = id;
        this.skillLevel = skillLevel;
        this.specialCourse = specialCourse;
        this.requiredSkills = requiredSkills;
    }

    public CourseToRequiredSkills(Course specialCourse, Skill requiredSkills, SkillLevel skillLevel) {
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

    public Course getSpecialCourse() {
        return specialCourse;
    }

    public void setSpecialCourse(Course specialCourse) {
        this.specialCourse = specialCourse;
    }

    public UUID getId() {
        return id;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }
}
