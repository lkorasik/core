package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.entity.UserRole;

@Component
public class UserRoleToDomainMapper implements Mapper<ru.urfu.mm.entity.UserRole, ru.urfu.mm.domain.UserRole> {
    @Override
    public ru.urfu.mm.domain.UserRole map(UserRole object) {
        return ru.urfu.mm.domain.UserRole.values()[object.ordinal()];
    }
}
