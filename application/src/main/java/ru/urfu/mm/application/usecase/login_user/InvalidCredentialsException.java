package ru.urfu.mm.application.usecase.login_user;

/**
 * Предоставлены неверные данные
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid credentials. Please check your token and password.");
    }
}
