package ru.urfu.mm.application.usecase.create;

import ru.urfu.mm.domain.exception.ApplicationException;

public class TooShortPasswordException extends ApplicationException {
    public TooShortPasswordException() {
        super("Password is too short. The password must be at least eight characters long.");
    }
}
