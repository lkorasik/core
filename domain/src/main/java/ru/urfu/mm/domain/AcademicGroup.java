package ru.urfu.mm.domain;

import ru.urfu.mm.domain.enums.Years;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Академическая группа.
 */
public class AcademicGroup {
    /**
     * Идентификатор группы
     */
    private final UUID id;
    /**
     * Номер группы (МЕНМ-ХХХХХХ)
     */
    private final String number;
    /**
     * Номер курса
     */
    private final Years year;
    /**
     * Список студентов
     */
    private final List<Student> students;
    /**
     * Базовый учебный план
     */
    private final BaseSyllabus baseSyllabus;

    public AcademicGroup(UUID id, String number, Years year, BaseSyllabus baseSyllabus) {
        this.id = id;
        this.number = number;
        this.year = year;
        this.students = new ArrayList<>();
        this.baseSyllabus = baseSyllabus;
    }
}
