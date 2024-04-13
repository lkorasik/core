package ru.urfu.mm.application.usecase.loginuser;

/**
 * Предоставлены неверные данные
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid credentials. Please check your login and password.");
    }
}
