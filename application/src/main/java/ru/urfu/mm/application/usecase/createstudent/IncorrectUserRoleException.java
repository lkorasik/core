package ru.urfu.mm.application.usecase.createstudent;

import java.util.UUID;

public class IncorrectUserRoleException extends RuntimeException {
    public IncorrectUserRoleException(UUID token) {
        super("The token type (" + token + ") does not match the account type");
    }
}