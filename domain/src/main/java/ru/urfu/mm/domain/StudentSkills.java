package ru.urfu.mm.domain;

import ru.urfu.mm.domain.enums.SkillLevel;

import java.util.UUID;

public class StudentSkills {
    private UUID id;
    private Student student;
    private Skill skill;
    private SkillLevel level;

    public StudentSkills() {

    }

    public StudentSkills(Student student, Skill skill, SkillLevel level) {
        this.student = student;
        this.skill = skill;
        this.level = level;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public UUID getId() {
        return id;
    }

    public SkillLevel getLevel() {
        return level;
    }
}
