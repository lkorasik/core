package ru.urfu.mm.persistance.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table
public class AccountEntity {
    @Id
    @Column
    private UUID login;
    @Column
    private String password;
    @Column
    private UserEntityRole role;

    public AccountEntity(UUID login, String password, UserEntityRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public AccountEntity() {
    }

    public UUID getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserEntityRole getRole() {
        return role;
    }
}
