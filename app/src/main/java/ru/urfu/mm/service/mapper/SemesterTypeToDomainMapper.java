package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.persistance.entity.enums.SemesterType;

@Component
public class SemesterTypeToDomainMapper implements Mapper<ru.urfu.mm.domain.enums.SemesterType, SemesterType> {
    @Override
    public SemesterType map(ru.urfu.mm.domain.enums.SemesterType object) {
        return SemesterType.values()[object.ordinal()];
    }
}
