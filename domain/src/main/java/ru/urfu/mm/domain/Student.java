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
    private final UUID id;
    /**
     * Аккаунт студента
     */
    private final Account account;
    /**
     * Индивидуальный учебный план
     */
    private final StudentSyllabus plan;
    /**
     * Список навыков, которыми обладает студент
     */
    private final List<Skill> skills;

    public Student(UUID id, Account account, StudentSyllabus plan) {
        this.id = id;
        this.account = account;
        this.plan = plan;
        skills = new ArrayList<>();
    }

    public Student(UUID id) {
        this.id = id;
        this.account = null;
        this.plan = null;
        this.skills = null;
    }

    public UUID getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }
}
