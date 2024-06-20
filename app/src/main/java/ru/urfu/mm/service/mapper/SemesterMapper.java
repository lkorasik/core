package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.persistance.entity.SemesterEntity;
import ru.urfu.mm.persistance.entity.enums.SemesterType;

@Component
public class SemesterMapper {
    public SemesterEntity toEntity(Semester semester) {
        return new SemesterEntity(
                semester.getId(),
                semester.getYear(),
                SemesterType.fromDomain(semester.getType())
        );
    }
}
