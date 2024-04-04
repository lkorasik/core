package ru.urfu.mm.domainlegacy;

import java.util.UUID;

public class StudentDesiredSkills {
    private UUID id;
    private Student student;
    private Skill skill;
    private SkillLevel level;

    public StudentDesiredSkills() {

    }

    public StudentDesiredSkills(Student student, Skill skill, SkillLevel level) {
        this.student = student;
        this.skill = skill;
        this.level = level;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public UUID getId() {
        return id;
    }

    public SkillLevel getLevel() {
        return level;
    }
}
