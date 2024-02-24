package ru.urfu.mm.core.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "student_skills")
public class StudentSkills {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "student_login")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;
    @Column
    private SkillLevel level;

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
