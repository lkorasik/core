package ru.urfu.mm.application.usecase.create;

import ru.urfu.mm.domain.exception.ApplicationException;

import java.util.UUID;

public class IncorrectUserRoleException extends ApplicationException {
    public IncorrectUserRoleException(UUID token) {
        super("The token type (" + token + ") does not match the account type");
    }
}