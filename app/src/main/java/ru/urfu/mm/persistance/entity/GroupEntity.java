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
    @OneToMany
    private List<StudentEntity> students;
    @OneToOne
    private BaseSyllabusEntity baseSyllabus;

    public GroupEntity() {
    }

    public GroupEntity(
            UUID id,
            String number,
            Years year,
            List<StudentEntity> students,
            BaseSyllabusEntity baseSyllabus
    ) {
        this.id = id;
        this.number = number;
        this.year = year;
        this.students = students;
        this.baseSyllabus = baseSyllabus;
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
