package ru.urfu.mm.application.usecase.create;

import ru.urfu.mm.domain.exception.ApplicationException;

import java.util.UUID;

public class RegistrationTokenNotExistException extends ApplicationException {
    public RegistrationTokenNotExistException(UUID token) {
        super("Registration token " + token + " does not exist");
    }
}
