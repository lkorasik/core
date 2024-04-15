package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.domain.User;
import ru.urfu.mm.entity.UserEntity;
import ru.urfu.mm.entity.UserRole;
import ru.urfu.mm.repository.UserRepository;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserGatewayImpl implements UserGateway {
    private final UserRepository userRepository;
    private final Mapper<UserRole, ru.urfu.mm.domain.UserRole> userRoleMapper;
    private final Mapper<ru.urfu.mm.domain.User, UserEntity> userMapper;

    @Autowired
    public UserGatewayImpl(
            UserRepository userRepository,
            Mapper<UserRole, ru.urfu.mm.domain.UserRole> userRoleMapper,
            Mapper<User, UserEntity> userMapper) {
        this.userRepository = userRepository;
        this.userRoleMapper = userRoleMapper;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        userRepository.save(userMapper.map(user));
    }

    @Override
    public User getByToken(UUID token) {
        UserEntity entity = userRepository.getReferenceById(token);
        return new User(
                entity.getLogin(),
                entity.getPassword(),
                ru.urfu.mm.domain.UserRole.values()[entity.getRole().ordinal()]
        );
    }

    @Override
    public Optional<User> findByToken(UUID token) {
        Optional<UserEntity> entity = userRepository.findById(token);
        return entity.map(x -> new User(
                x.getLogin(),
                x.getPassword(),
                userRoleMapper.map(x.getRole())
        ));
    }
}
