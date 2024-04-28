package ru.urfu.mm.persistance.entity;

import java.util.UUID;

import jakarta.persistence.*;
import ru.urfu.mm.persistance.entity.enums.SkillLevel;

@Entity
@Table(name = "courses_to_result_skills")
public class CourseToResultSkills {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "special_course_id")
    private SpecialCourse specialCourse;
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;
    @Column
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
