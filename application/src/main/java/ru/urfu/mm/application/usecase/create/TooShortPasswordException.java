package ru.urfu.mm.application.usecase.create;

public class TooShortPasswordException extends RuntimeException {
    public TooShortPasswordException() {
        super("Password is too short. The password must be at least eight characters long.");
    }
}
