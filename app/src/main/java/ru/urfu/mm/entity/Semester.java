package ru.urfu.mm.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "semesters")
public class Semester {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @Column
    private int year;
    @Column
    private int semesterNumber;
    @Column
    private SemesterType type;

    public Semester() {
    }

    public Semester(UUID id, int year, int semesterNumber, SemesterType type) {
        this.id = id;
        this.year = year;
        this.semesterNumber = semesterNumber;
        this.type = type;
    }

    public Semester(int year, int semesterNumber, SemesterType type) {
        this.year = year;
        this.semesterNumber = semesterNumber;
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

    public int getSemesterNumber() {
        return semesterNumber;
    }
}
