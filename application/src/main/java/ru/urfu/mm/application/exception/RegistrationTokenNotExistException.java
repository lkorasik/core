package ru.urfu.mm.application.exception;

import java.util.UUID;

public class RegistrationTokenNotExistException extends RuntimeException {
    public RegistrationTokenNotExistException(UUID token) {
        super("Registration token " + token + " does not exist");
    }
}
