package ru.urfu.mm.domain;

/**
 * Семестр.
 * Представляет собой один семестр.
 */
public class Semester {
    /**
     * Академический год, в котором этот семестр
     */
    private final AcademicYear year;
    /**
     * Тип семестра
     */
    private final SemesterType type;

    public Semester(AcademicYear year, SemesterType type) {
        this.year = year;
        this.type = type;
    }
}
