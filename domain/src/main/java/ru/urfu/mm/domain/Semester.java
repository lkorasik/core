package ru.urfu.mm.domain;

import ru.urfu.mm.domain.enums.SemesterType;

import java.util.UUID;

/**
 * Семестр
 */
public class Semester {
    /**
     * Идентификатор семестра
     */
    private UUID id;
    /**
     * Год, в котором проходит семестр
     */
    private int year;
    /**
     * Тип семестра
     */
    private SemesterType type;

    public Semester(UUID id, int year, SemesterType type) {
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
