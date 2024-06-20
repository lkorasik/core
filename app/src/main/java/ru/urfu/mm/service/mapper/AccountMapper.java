package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.domain.Account;
import ru.urfu.mm.persistance.entity.AccountEntity;
import ru.urfu.mm.persistance.entity.enums.UserEntityRole;

@Component
public class AccountMapper {
    public AccountEntity toEntity(Account account) {
        return new AccountEntity(
                account.getToken(),
                account.getPassword(),
                UserEntityRole.fromDomain(account.getRole())
        );
    }
}
