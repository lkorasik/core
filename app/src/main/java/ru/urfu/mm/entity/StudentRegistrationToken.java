package ru.urfu.mm.entity;

import jakarta.persistence.*;

import java.util.UUID;

/**
 * Регистрационный токен студента.
 */
@Entity
@Table
public class StudentRegistrationToken {
    @Id
    public UUID token;
    @ManyToOne
    @JoinColumn(name = "groups_id")
    public GroupEntity group;

    public StudentRegistrationToken() {
    }

    public StudentRegistrationToken(UUID token, GroupEntity group) {
        this.token = token;
        this.group = group;
    }
}
