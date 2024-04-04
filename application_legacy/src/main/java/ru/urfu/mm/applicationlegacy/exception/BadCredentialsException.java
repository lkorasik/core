package ru.urfu.mm.applicationlegacy.exception;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException() {
        super("Bad credentials");
    }
}
