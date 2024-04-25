package ru.urfu.mm.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.domain.Account;
import ru.urfu.mm.entity.UserEntity;
import ru.urfu.mm.entity.UserEntityRole;

@Component
public class UserToEntityMapper implements Mapper<Account, UserEntity> {
    private final Mapper<ru.urfu.mm.domain.UserRole, UserEntityRole> roleMapper;

    @Autowired
    public UserToEntityMapper(Mapper<ru.urfu.mm.domain.UserRole, UserEntityRole> roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public UserEntity map(Account account) {
        return new UserEntity(
                account.login(),
                account.password(),
                roleMapper.map(account.role())
        );
    }
}
