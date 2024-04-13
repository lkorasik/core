package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.entity.SemesterType;

@Component
public class SemesterTypeToDomainMapper implements Mapper<ru.urfu.mm.domain.SemesterType, ru.urfu.mm.entity.SemesterType> {
    @Override
    public SemesterType map(ru.urfu.mm.domain.SemesterType object) {
        return ru.urfu.mm.entity.SemesterType.values()[object.ordinal()];
    }
}
