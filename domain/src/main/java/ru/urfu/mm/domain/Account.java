package ru.urfu.mm.domain;

import java.util.UUID;

/**
 * Аккаунт пользователя.
 */
public record Account(UUID login, String password, UserRole role) {
}
