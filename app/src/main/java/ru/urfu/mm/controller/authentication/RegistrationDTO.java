package ru.urfu.mm.controller.authentication;

public record RegistrationDTO(
        String token,
        String password,
        String passwordAgain
) { }
