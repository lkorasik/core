package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Account;

import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    void save(Account account);
    Optional<Account> findByToken(UUID token);
}
