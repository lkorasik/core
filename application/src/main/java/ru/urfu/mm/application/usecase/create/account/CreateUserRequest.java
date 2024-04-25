package ru.urfu.mm.application.usecase.create.account;

import java.util.UUID;

public record CreateUserRequest(
        UUID token,
        String password,
        String passwordAgain
) {
}
