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
    private final UUID id;
    /**
     * Год, в котором проходит семестр
     */
    private final int year;
    /**
     * Тип семестра
     */
    private final SemesterType type;

    public Semester(UUID id, int year, SemesterType type) {
        this.id = id;
        this.year = year;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public SemesterType getType() {
        return type;
    }

    public int getYear() {
        return year;
    }
}
