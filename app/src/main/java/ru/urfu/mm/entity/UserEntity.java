package ru.urfu.mm.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column
    private UUID login;
    @Column
    private String password;
    @Column
    private UserRole role;

    public UserEntity(UUID login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public UserEntity() {
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
