package ru.urfu.mm.application.gateway;

public interface PasswordGateway {
    String encode(String password);
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
