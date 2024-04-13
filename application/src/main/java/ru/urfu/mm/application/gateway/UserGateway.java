package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    void save(User user);
    User getByToken(UUID token);
    Optional<User> findByToken(UUID token);
}
