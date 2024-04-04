package ru.urfu.mm.controller.authentication;

public record RegistrationAdministratorDTO(
        String token,
        String password,
        String passwordAgain
) { }
