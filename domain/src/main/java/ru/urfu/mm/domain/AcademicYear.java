package ru.urfu.mm.domain;

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
}
