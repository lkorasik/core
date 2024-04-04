package ru.urfu.mm.applicationlegacy.gateway;

public interface PasswordGateway {
    String encode(String password);
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
