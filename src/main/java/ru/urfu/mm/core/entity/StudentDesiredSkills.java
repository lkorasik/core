package ru.urfu.mm.core.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "students_desired_skills")
public class StudentDesiredSkills {
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
