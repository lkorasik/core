package ru.urfu.mm.domain;

import ru.urfu.mm.domain.enums.SkillLevel;

import java.util.UUID;

/**
 * Курс и преобритаемый скиллы
 */
public class CourseToResultSkills {
    private UUID id;
    private Course specialCourse;
    private Skill skill;
    private SkillLevel skillLevel;

    public CourseToResultSkills() {
    }

    public CourseToResultSkills(UUID id, Course specialCourse, Skill skill, SkillLevel skillLevel) {
        this.id = id;
        this.specialCourse = specialCourse;
        this.skill = skill;
        this.skillLevel = skillLevel;
    }

    public CourseToResultSkills(Course specialCourse, Skill skill, SkillLevel skillLevel) {
        this.specialCourse = specialCourse;
        this.skill = skill;
        this.skillLevel = skillLevel;
    }

    public Course getSpecialCourse() {
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
