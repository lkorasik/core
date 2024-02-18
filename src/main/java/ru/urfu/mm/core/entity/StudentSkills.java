package ru.urfu.mm.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "student_skills")
public class StudentSkills {
    @Id
    @Column
    // [ForeignKey("Student")]
    private String studentLogin;
    @Column
    // [ForeignKey("Skill")]
    private UUID skillId;
    @Column
    private SkillLevel level;

//    public Student Student { get; set; }
//    public Skill Skill { get; set; }
}
