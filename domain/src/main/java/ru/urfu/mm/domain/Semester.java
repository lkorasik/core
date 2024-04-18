package ru.urfu.mm.domain;

import java.util.UUID;

public class Semester {
    private UUID id;
    private int year;
    private SemesterType type;

    public Semester() {
    }

    public Semester(UUID id, int year, SemesterType type) {
        this.id = id;
        this.year = year;
        this.type = type;
    }

    public Semester(int year, SemesterType type) {
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
