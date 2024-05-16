package ru.urfu.mm.application.usecase.login_user;

import java.util.UUID;

public record LoginRequest(
        UUID token,
        String password
) {
}
