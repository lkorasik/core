package ru.urfu.mm.domain;

import ru.urfu.mm.domain.enums.UserRole;

import java.util.UUID;

/**
 * Аккаунт пользователя
 * @param token Токен пользователя
 * @param password Пароль
 * @param role Роль аккаунта
 */
public record Account(UUID token, String password, UserRole role) {
}
