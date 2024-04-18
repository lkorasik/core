package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Академическая группа.
 */
public class Group {
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

    public Group(UUID id, String number) {
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
