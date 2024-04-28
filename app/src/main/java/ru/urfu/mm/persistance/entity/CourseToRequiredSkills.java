package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;
import ru.urfu.mm.persistance.entity.enums.SkillLevel;

import java.util.UUID;

@Entity
@Table(name = "courses_to_required_skills")
public class CourseToRequiredSkills {
    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private SkillLevel skillLevel;
    @ManyToOne
    @JoinColumn(name = "special_course_id")
    private SpecialCourse specialCourse;
    @ManyToOne
    @JoinColumn(name = "required_skills_id")
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
