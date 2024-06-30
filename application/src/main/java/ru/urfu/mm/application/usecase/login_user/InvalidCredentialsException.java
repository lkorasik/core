package ru.urfu.mm.application.usecase.login_user;

import ru.urfu.mm.domain.exception.ApplicationException;

/**
 * Предоставлены неверные данные
 */
public class InvalidCredentialsException extends ApplicationException {
    public InvalidCredentialsException() {
        super("Invalid credentials. Please check your token and password.");
    }
}
