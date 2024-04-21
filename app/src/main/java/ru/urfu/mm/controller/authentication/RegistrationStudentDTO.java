package ru.urfu.mm.controller.authentication;

public record RegistrationStudentDTO(
        String token,
        String password,
        String passwordAgain
) { }
