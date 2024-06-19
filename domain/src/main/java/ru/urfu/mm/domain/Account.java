package ru.urfu.mm.domain;

import ru.urfu.mm.domain.enums.UserRole;

import java.util.UUID;

/**
 * Аккаунт пользователя
 */
public class Account {
    /**
     * Токен пользователя
     */
    private final UUID token;
    /**
     * Пароль пользователя
     */
    private final String password;
    /**
     * Роль пользователя
     */
    private final UserRole role;

    public Account(UUID token, String password, UserRole role) {
        this.token = token;
        this.password = password;
        this.role = role;
    }
}
