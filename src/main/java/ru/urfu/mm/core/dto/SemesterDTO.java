package ru.urfu.mm.core.dto;

import java.util.UUID;

public class SemesterDTO {
    private final UUID id;
    private final int year;
    private final int semesterNumber;

    public SemesterDTO(UUID id, int year, int semesterNumber) {
        this.id = id;
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
