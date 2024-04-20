package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Group;
import ru.urfu.mm.domain.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenGateway {
    Optional<UserRole> getRoleByToken(UUID token);
    void deleteToken(UUID token);
    List<UUID> generateStudentRegistrationTokens(int count, Group group);
    List<UUID> getTokensByGroup(Group group);
    Optional<Group> getStudentRegistrationToken(UUID token);
    void deleteStudentRegistrationToken(UUID token);
}
