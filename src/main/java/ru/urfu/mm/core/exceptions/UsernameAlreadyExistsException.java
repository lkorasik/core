package ru.urfu.mm.core.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Username " + username + " is already taken");
    }
}
