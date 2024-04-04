package ru.urfu.mm.application.exception;

public class IncorrectUserRoleException extends RuntimeException {
    public IncorrectUserRoleException(String token) {
        super("The token type (" + token + ") does not match the account type");
    }
}