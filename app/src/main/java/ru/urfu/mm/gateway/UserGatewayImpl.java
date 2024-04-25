package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.domain.Account;
import ru.urfu.mm.entity.UserEntity;
import ru.urfu.mm.entity.UserEntityRole;
import ru.urfu.mm.repository.UserRepository;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserGatewayImpl implements UserGateway {
    private final UserRepository userRepository;
    private final Mapper<UserEntityRole, ru.urfu.mm.domain.UserRole> userRoleMapper;
    private final Mapper<Account, UserEntity> userMapper;

    @Autowired
    public UserGatewayImpl(
            UserRepository userRepository,
            Mapper<UserEntityRole, ru.urfu.mm.domain.UserRole> userRoleMapper,
            Mapper<Account, UserEntity> userMapper) {
        this.userRepository = userRepository;
        this.userRoleMapper = userRoleMapper;
        this.userMapper = userMapper;
    }

    @Override
    public void save(Account account) {
        userRepository.save(userMapper.map(account));
    }

    @Override
    public Account getByToken(UUID token) {
        UserEntity entity = userRepository.getReferenceById(token);
        return new Account(
                entity.getLogin(),
                entity.getPassword(),
                ru.urfu.mm.domain.UserRole.values()[entity.getRole().ordinal()]
        );
    }

    @Override
    public Optional<Account> findByToken(UUID token) {
        Optional<UserEntity> entity = userRepository.findById(token);
        return entity.map(x -> new Account(
                x.getLogin(),
                x.getPassword(),
                userRoleMapper.map(x.getRole())
        ));
    }
}
