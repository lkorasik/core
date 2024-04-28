package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "groups")
public class GroupEntity {
    @Id
    @Column
    private UUID id;
    @Column
    private String number;
    @Column
    @Enumerated
    private Years year;
    @OneToMany(mappedBy = "group")
    private List<StudentEntity> studentEntities;
    @ManyToOne
    @JoinColumn(name="educational_programs_id")
    private EducationalProgram educationalProgram;

    public EducationalProgram getEducationalProgram() {
        return educationalProgram;
    }

    public void setEducationalProgram(EducationalProgram educationalProgram) {
        this.educationalProgram = educationalProgram;
    }

    public GroupEntity() {
    }

    public GroupEntity(UUID id, String number, Years year) {
        this.id = id;
        this.number = number;
        this.year = year;
    }

    public UUID getId() {
        return id;
    }

    public Years getYear() {
        return year;
    }

    public List<StudentEntity> getStudentEntities() {
        return studentEntities;
    }

    public String getNumber() {
        return number;
    }
}
