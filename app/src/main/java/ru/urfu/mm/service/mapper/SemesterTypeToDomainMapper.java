package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.persistance.entity.SemesterType;

@Component
public class SemesterTypeToDomainMapper implements Mapper<ru.urfu.mm.domain.SemesterType, SemesterType> {
    @Override
    public SemesterType map(ru.urfu.mm.domain.SemesterType object) {
        return SemesterType.values()[object.ordinal()];
    }
}
