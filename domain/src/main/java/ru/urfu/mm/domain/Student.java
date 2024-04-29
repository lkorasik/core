package ru.urfu.mm.domain;

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
    private StudyPlan individualPlan;

    public Student(UUID id, Account account) {
        this.id = id;
        this.account = account;
    }

    public Student(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }
}
