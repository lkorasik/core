package ru.urfu.mm.domain;

/**
 * Пользователь.
 * Представляет собой пользователя нашей системы.
 */
public abstract class User {
    /**
     * Токен доступа
     */
    private final String token;
    /**
     * Пароль
     */
    private final String password;

    public User(String token, String password) {
        this.token = token;
        this.password = password;
    }
}
