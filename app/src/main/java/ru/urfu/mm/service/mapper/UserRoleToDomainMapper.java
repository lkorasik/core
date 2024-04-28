package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.domain.UserRole;
import ru.urfu.mm.persistance.entity.UserEntityRole;

@Component
public class UserRoleToDomainMapper implements Mapper<UserEntityRole, UserRole> {
    @Override
    public UserRole map(UserEntityRole object) {
        return UserRole.values()[object.ordinal()];
    }
}
