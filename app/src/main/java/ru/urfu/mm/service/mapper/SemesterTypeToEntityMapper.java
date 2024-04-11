package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.domain.SemesterType;

@Component
public class SemesterTypeToEntityMapper implements Mapper<ru.urfu.mm.entity.SemesterType, ru.urfu.mm.domain.SemesterType> {
    @Override
    public SemesterType map(ru.urfu.mm.entity.SemesterType object) {
        return ru.urfu.mm.domain.SemesterType.values()[object.ordinal()];
    }
}
