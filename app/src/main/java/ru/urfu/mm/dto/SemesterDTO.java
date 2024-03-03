package ru.urfu.mm.dto;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SemesterDTO that = (SemesterDTO) o;
        return year == that.year && semesterNumber == that.semesterNumber && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, semesterNumber);
    }
}
