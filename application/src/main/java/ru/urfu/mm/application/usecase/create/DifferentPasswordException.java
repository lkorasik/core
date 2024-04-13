package ru.urfu.mm.application.usecase.create;

public class DifferentPasswordException extends RuntimeException {
    public DifferentPasswordException() {
        super("The passwords entered by the user do not match.");
    }
}
