package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;
import ru.urfu.mm.persistance.entity.enums.SkillLevel;

import java.util.UUID;

@Deprecated
@Entity
@Table(name = "students_desired_skills")
public class StudentDesiredSkills {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "student_login")
    private StudentEntity studentEntity;
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;
    @Column
    private SkillLevel level;

    public StudentDesiredSkills() {

    }

    public StudentDesiredSkills(StudentEntity studentEntity, Skill skill, SkillLevel level) {
        this.studentEntity = studentEntity;
        this.skill = skill;
        this.level = level;
    }

    public StudentEntity getStudent() {
        return studentEntity;
    }

    public void setStudent(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
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
