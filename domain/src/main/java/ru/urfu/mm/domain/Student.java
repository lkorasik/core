package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Студент
 */
public class Student {
    /**
     * Идентификатор студента
     */
    private UUID id;
    /**
     * Аккаунт студента
     */
    private Account account;
    /**
     * Индивидуальный учебный план
     */
    private Syllabus plan;
    /**
     * Список навыков, которыми обладает студент
     */
    private List<Skill> skills;

    public Student(UUID id) {
        this.id = id;
        this.account = null;
        this.plan = null;
        this.skills = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }
}
