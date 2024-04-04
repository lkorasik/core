package ru.urfu.mm.controller.authentication;

import java.util.UUID;

public record RegistrationStudentDTO(
        String token,
        UUID programId,
        String group,
        String password,
        String passwordAgain
) { }
