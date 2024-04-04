package ru.urfu.mm.application.exception;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException() {
        super("Bad credentials");
    }
}
