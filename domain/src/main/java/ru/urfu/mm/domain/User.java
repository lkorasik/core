package ru.urfu.mm.domain;

import java.util.UUID;

public class User {
    private UUID login;
    private String password;
    private UserRole role;

    public User(UUID login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public UUID getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }
}
