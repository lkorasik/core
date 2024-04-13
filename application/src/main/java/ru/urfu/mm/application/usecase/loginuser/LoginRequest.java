package ru.urfu.mm.application.usecase.loginuser;

import java.util.UUID;

public record LoginRequest(
        UUID token,
        String password
) {
}
