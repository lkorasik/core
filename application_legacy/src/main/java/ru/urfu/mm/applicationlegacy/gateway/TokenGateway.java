package ru.urfu.mm.applicationlegacy.gateway;

import ru.urfu.mm.domainlegacy.UserRole;

import java.util.Optional;
import java.util.UUID;

public interface TokenGateway {
    Optional<UserRole> getRoleByToken(UUID token);
    void deleteToken(UUID token);
}
