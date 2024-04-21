package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Group;

import java.util.List;
import java.util.UUID;

public interface TokenGateway {
    void deleteToken(UUID token);
    List<UUID> getTokensByGroup(Group group);
    boolean isAdministratorToken(UUID token);
    boolean isStudentToken(UUID token);
}
