package ru.urfu.mm.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @Column
    private String number;
    @Column
    @Enumerated
    private Years year;
    @OneToMany(mappedBy = "group")
    private List<StudentEntity> studentEntities;

    public String getNumber() {
        return number;
    }
}
