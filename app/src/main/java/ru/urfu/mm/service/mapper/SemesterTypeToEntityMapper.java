package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.domain.enums.SemesterType;

@Component
public class SemesterTypeToEntityMapper implements Mapper<ru.urfu.mm.persistance.entity.enums.SemesterType, SemesterType> {
    @Override
    public SemesterType map(ru.urfu.mm.persistance.entity.enums.SemesterType object) {
        return SemesterType.values()[object.ordinal()];
    }
}
