package ru.urfu.mm.application.usecase.create.account;

import java.util.UUID;

public record CreateAccountRequest(
        UUID token,
        String password,
        String passwordAgain
) { }
