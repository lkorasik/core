package ru.urfu.mm.applicationlegacy.gateway;

import ru.urfu.mm.domainlegacy.User;

import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    void save(User user);
    User getByToken(UUID token);
}
