package ru.urfu.mm.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Semester semester = (Semester) obj;
        return Objects.equals(year, semester.year) && type == semester.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, type);
    }
}
