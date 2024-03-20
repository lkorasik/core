package ru.urfu.mm.controller;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Контроллер, с методом для получения токена пользователя
 */
public abstract class AbstractAuthorizedController {
    /**
     * Получить токен пользователя из {@link SecurityContextHolder}
     */
    public String getUserToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
