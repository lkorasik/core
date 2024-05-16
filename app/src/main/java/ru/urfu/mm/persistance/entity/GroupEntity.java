package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;
import ru.urfu.mm.persistance.entity.enums.Years;

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
    @ManyToOne
    @JoinColumn(name = "program_id")
    private EducationalProgramEntity program;
    @OneToMany(mappedBy = "group")
    private List<StudentEntity> students;

    public EducationalProgramEntity getProgram() {
        return program;
    }

    public void setProgram(EducationalProgramEntity educationalProgramEntity) {
        this.program = educationalProgramEntity;
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

    public String getNumber() {
        return number;
    }

    public List<StudentEntity> getStudents() {
        return students;
    }
}
