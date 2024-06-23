package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.gateway.SemesterGateway;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.persistance.entity.SemesterEntity;
import ru.urfu.mm.persistance.entity.enums.SemesterType;
import ru.urfu.mm.persistance.repository.SemesterRepository;
import ru.urfu.mm.service.mapper.SemesterMapper;

import java.util.List;
import java.util.UUID;

@Component
public class SemesterGatewayImpl implements SemesterGateway {
    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;

    @Autowired
    public SemesterGatewayImpl(SemesterRepository semesterRepository, SemesterMapper semesterMapper) {
        this.semesterRepository = semesterRepository;
        this.semesterMapper = semesterMapper;
    }

    @Override
    public void save(Semester semester) {
        SemesterEntity entity = semesterMapper.toEntity(semester);
        semesterRepository.save(entity);
    }

    @Override
    public Semester getById(UUID semesterId) {
        SemesterEntity entity = semesterRepository.getReferenceById(semesterId);
        return new Semester(
                entity.getId(),
                entity.getYear(),
                SemesterType.toDomain(entity.getType())
        );
    }

    @Override
    public List<Semester> getSemestersForEntireStudyPeriod(int startYear) {
        return semesterRepository
                .findAll()
                .stream()
                .filter(x -> isActualSemester(x, startYear))
                .map(x -> new Semester(
                        x.getId(),
                        x.getYear(),
                        SemesterType.toDomain(x.getType())
                ))
                .toList();
    }

    private boolean isActualSemester(SemesterEntity semesterEntity, int startYear) {
        return isFirstSemester(semesterEntity, startYear) || isSecondSemester(semesterEntity, startYear)
                || isThirdSemester(semesterEntity, startYear) || isFourthSemester(semesterEntity, startYear);
    }

    private boolean isFirstSemester(SemesterEntity semesterEntity, int startYear) {
        return (semesterEntity.getType() == SemesterType.FALL) && (semesterEntity.getYear() == startYear);
    }

    private boolean isSecondSemester(SemesterEntity semesterEntity, int startYear) {
        return (semesterEntity.getType() == SemesterType.SPRING) && (semesterEntity.getYear() == startYear + 1);
    }

    private boolean isThirdSemester(SemesterEntity semesterEntity, int startYear) {
        return (semesterEntity.getType() == SemesterType.FALL) && (semesterEntity.getYear() == startYear + 1);
    }

    private boolean isFourthSemester(SemesterEntity semesterEntity, int startYear) {
        return (semesterEntity.getType() == SemesterType.SPRING) && (semesterEntity.getYear() == startYear + 2);
    }
}
