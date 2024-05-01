package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;

import java.util.UUID;

/**
 * Регистрационный токен студента.
 */
@Entity
@Table
public class StudentRegistrationToken {
    @Id
    private UUID token;
    @ManyToOne
//    @JoinColumn(name = "groups_id")
    private GroupEntity group;

    public StudentRegistrationToken() {
    }

    public StudentRegistrationToken(UUID token, GroupEntity group) {
        this.token = token;
        this.group = group;
    }

    public UUID getToken() {
        return token;
    }

    public GroupEntity getGroup() {
        return group;
    }
}
