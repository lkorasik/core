package ru.urfu.mm.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.domain.Account;
import ru.urfu.mm.persistance.entity.AccountEntity;
import ru.urfu.mm.persistance.entity.enums.UserEntityRole;

@Component
public class UserToEntityMapper implements Mapper<Account, AccountEntity> {
    private final Mapper<ru.urfu.mm.domain.UserRole, UserEntityRole> roleMapper;

    @Autowired
    public UserToEntityMapper(Mapper<ru.urfu.mm.domain.UserRole, UserEntityRole> roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public AccountEntity map(Account account) {
        return new AccountEntity(
                account.login(),
                account.password(),
                roleMapper.map(account.role())
        );
    }
}
