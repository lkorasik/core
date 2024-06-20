package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;
import ru.urfu.mm.persistance.entity.enums.SemesterType;

import java.util.UUID;

@Entity
@Table(name = "semesters")
public class SemesterEntity {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @Column
    private int year;
    @Column
    private SemesterType type;

    public SemesterEntity() {
    }

    public SemesterEntity(UUID id, int year, SemesterType type) {
        this.id = id;
        this.year = year;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public SemesterType getType() {
        return type;
    }
}
