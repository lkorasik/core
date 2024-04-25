package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Account;

import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    void save(Account account);
    Account getByToken(UUID token);
    Optional<Account> findByToken(UUID token);
}
