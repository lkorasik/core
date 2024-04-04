package ru.urfu.mm.domain;

import java.util.Objects;

/**
 * Академический год
 */
public class AcademicYear {
    /**
     * Номер года, в котором проходит осенний семестр
     */
    private final int year;

    public AcademicYear(int startYear) {
        this.year = startYear;
    }

    /**
     * Получить год, в котором проходит первый семестр этого учебного года
     */
    public int getFirstSemesterYear() {
        return year;
    }

    /**
     * Получить год, в котором проходит второй семестр этого учебного года
     */
    public int getSecondSemesterYear() {
        return year + 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        AcademicYear that = (AcademicYear) obj;
        return year == that.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year);
    }
}
