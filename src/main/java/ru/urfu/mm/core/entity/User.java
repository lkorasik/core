package ru.urfu.mm.core.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public UUID login;

    @Column
    public String password;

    @Column
    public UserRole role;

    public User(UUID login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User() {
    }
}
