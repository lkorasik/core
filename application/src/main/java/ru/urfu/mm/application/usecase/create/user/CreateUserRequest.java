package ru.urfu.mm.application.usecase.create.user;

import java.util.UUID;

public record CreateUserRequest(
        UUID token,
        String password,
        String passwordAgain
) {
}
