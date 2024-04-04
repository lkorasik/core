package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.UserRole;

import java.util.Optional;
import java.util.UUID;

public interface TokenGateway {
    Optional<UserRole> getRoleByToken(UUID token);
    void deleteToken(UUID token);
}
