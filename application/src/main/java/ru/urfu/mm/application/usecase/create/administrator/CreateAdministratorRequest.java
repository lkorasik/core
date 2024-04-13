package ru.urfu.mm.application.usecase.create.administrator;

import java.util.UUID;

public record CreateAdministratorRequest(
        UUID token,
        String password,
        String passwordAgain
) {
}
