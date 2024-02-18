package ru.urfu.mm.core.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "students_desired_skills")
public class StudentDesiredSkills {
    @Id
    @Column
    private String studentLogin;
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;
    @Column
    private SkillLevel level;

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

//    public Student Student { get; set; }
//    public Skill Skill { get; set; }
}
