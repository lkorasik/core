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

    public Semester() {
    }

    public Semester(UUID id, int year, int semesterNumber) {
        this.id = id;
        this.year = year;
        this.semesterNumber = semesterNumber;
    }

    public Semester(int year, int semesterNumber) {
        this.year = year;
        this.semesterNumber = semesterNumber;
    }

    public UUID getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }
}
