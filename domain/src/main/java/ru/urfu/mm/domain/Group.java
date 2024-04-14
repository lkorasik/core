package ru.urfu.mm.domain;

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
}
