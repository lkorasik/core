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
    private UUID id;
    /**
     * Номер группы (МЕНМ-ХХХХХХ)
     */
    private String number;
    /**
     * Номер курса
     */
    private Years year;
    /**
     * Список студентов
     */
    private List<Student> students;

    public AcademicGroup(UUID id, String number) {
        this.id = id;
        this.number = number;
        year = Years.FIRST;
        students = new ArrayList<>();
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
}
