package ru.urfu.mm.domain;

import java.util.UUID;

public class Semester {
    private UUID id;
    private int year;
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
