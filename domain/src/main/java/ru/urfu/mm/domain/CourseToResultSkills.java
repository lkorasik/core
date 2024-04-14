package ru.urfu.mm.domain;

import java.util.UUID;

/**
 * Курс и преобритаемый скиллы
 */
public class CourseToResultSkills {
    private UUID id;
    private SpecialCourse specialCourse;
    private Skill skill;
    private SkillLevel skillLevel;

    public CourseToResultSkills() {
    }

    public CourseToResultSkills(UUID id, SpecialCourse specialCourse, Skill skill, SkillLevel skillLevel) {
        this.id = id;
        this.specialCourse = specialCourse;
        this.skill = skill;
        this.skillLevel = skillLevel;
    }

    public CourseToResultSkills(SpecialCourse specialCourse, Skill skill, SkillLevel skillLevel) {
        this.specialCourse = specialCourse;
        this.skill = skill;
        this.skillLevel = skillLevel;
    }

    public SpecialCourse getSpecialCourse() {
        return specialCourse;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
