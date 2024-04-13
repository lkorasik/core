package ru.urfu.mm.application.usecase.createstudent;

public class DifferentPasswordException extends RuntimeException {
    public DifferentPasswordException() {
        super("The passwords entered by the user do not match.");
    }
}
