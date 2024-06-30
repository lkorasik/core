package ru.urfu.mm.application.usecase.create;

import ru.urfu.mm.domain.exception.ApplicationException;

public class DifferentPasswordException extends ApplicationException {
    public DifferentPasswordException() {
        super("The passwords entered by the user do not match.");
    }
}
