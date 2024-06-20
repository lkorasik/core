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

    public AcademicGroup(UUID id, String number, BaseSyllabus baseSyllabus) {
        this.id = id;
        this.number = number;
        this.year = Years.FIRST;
        this.students = new ArrayList<>();
        this.baseSyllabus = baseSyllabus;
    }

    public UUID getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Years getYear() {
        return year;
    }

    public List<Student> getStudents() {
        return students;
    }

    public BaseSyllabus getBaseSyllabus() {
        return baseSyllabus;
    }
}
