package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.domain.Account;
import ru.urfu.mm.domain.enums.UserRole;
import ru.urfu.mm.persistance.entity.AccountEntity;
import ru.urfu.mm.persistance.entity.enums.UserEntityRole;
import ru.urfu.mm.persistance.repository.AccountRepository;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserGatewayImpl implements UserGateway {
    private final AccountRepository accountRepository;
    private final Mapper<UserEntityRole, UserRole> userRoleMapper;
    private final Mapper<Account, AccountEntity> userMapper;

    @Autowired
    public UserGatewayImpl(
            AccountRepository accountRepository,
            Mapper<UserEntityRole, UserRole> userRoleMapper,
            Mapper<Account, AccountEntity> userMapper) {
        this.accountRepository = accountRepository;
        this.userRoleMapper = userRoleMapper;
        this.userMapper = userMapper;
    }

    @Override
    public void save(Account account) {
        accountRepository.save(userMapper.map(account));
    }

    @Override
    public Account getByToken(UUID token) {
        AccountEntity entity = accountRepository.getReferenceById(token);
        return new Account(
                entity.getLogin(),
                entity.getPassword(),
                UserRole.values()[entity.getRole().ordinal()]
        );
    }

    @Override
    public Optional<Account> findByToken(UUID token) {
        Optional<AccountEntity> entity = accountRepository.findById(token);
        return entity.map(x -> new Account(
                x.getLogin(),
                x.getPassword(),
                userRoleMapper.map(x.getRole())
        ));
    }
}
