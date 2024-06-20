package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.domain.Account;
import ru.urfu.mm.persistance.entity.AccountEntity;
import ru.urfu.mm.persistance.entity.enums.UserEntityRole;

@Component
public class UserToEntityMapper implements Mapper<Account, AccountEntity> {
    @Override
    public AccountEntity map(Account account) {
        throw new NotImplementedException();
//        return new AccountEntity(
//                account.token(),
//                account.password(),
//                UserEntityRole.fromDomain(account.role())
//        );
    }
}
