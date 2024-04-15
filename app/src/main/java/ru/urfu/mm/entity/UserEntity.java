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
    private UserEntityRole role;

    public UserEntity(UUID login, String password, UserEntityRole role) {
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

    public UserEntityRole getRole() {
        return role;
    }
}
