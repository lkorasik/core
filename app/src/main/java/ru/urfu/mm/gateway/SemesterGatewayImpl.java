package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.SemesterGateway;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.entity.SemesterType;
import ru.urfu.mm.repository.SemesterRepository;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.List;
import java.util.UUID;

@Component
public class SemesterGatewayImpl implements SemesterGateway {
    private final SemesterRepository semesterRepository;
    private final Mapper<ru.urfu.mm.entity.SemesterType, ru.urfu.mm.domain.SemesterType> semesterTypeMapper;

    @Autowired
    public SemesterGatewayImpl(
            SemesterRepository semesterRepository,
            Mapper<ru.urfu.mm.entity.SemesterType, ru.urfu.mm.domain.SemesterType> semesterTypeMapper) {
        this.semesterRepository = semesterRepository;
        this.semesterTypeMapper = semesterTypeMapper;
    }

    @Override
    public void save(Semester semester) {
        ru.urfu.mm.entity.Semester entity;
        if (semester.getId() != null) {
            entity = new ru.urfu.mm.entity.Semester(
                    semester.getId(),
                    semester.getYear(),
                    SemesterType.values()[semester.getType().ordinal()]
            );
        } else {
            entity = new ru.urfu.mm.entity.Semester(
                    semester.getYear(),
                    SemesterType.values()[semester.getType().ordinal()]
            );
        }
        semesterRepository.save(entity);
    }

    @Override
    public Semester getById(UUID semesterId) {
        ru.urfu.mm.entity.Semester entity = semesterRepository.getReferenceById(semesterId);
        return new Semester(
                entity.getId(),
                entity.getYear(),
                semesterTypeMapper.map(entity.getType())
        );
    }

    @Override
    public List<Semester> GetLaterOrEqual(int year) {
        return semesterRepository
                .findAll()
                .stream()
                .filter(semester -> semester.getYear() >= year)
                .map(x -> new Semester(
                        x.getId(),
                        x.getYear(),
                        semesterTypeMapper.map(x.getType())
                ))
                .toList();
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
                        semesterTypeMapper.map(x.getType())
                ))
                .toList();
    }

    private boolean isActualSemester(ru.urfu.mm.entity.Semester semester, int startYear) {
        return isFirstSemester(semester, startYear) || isSecondSemester(semester, startYear)
                || isThirdSemester(semester, startYear) || isFourthSemester(semester, startYear);
    }

    private boolean isFirstSemester(ru.urfu.mm.entity.Semester semester, int startYear) {
        return (semester.getType() == ru.urfu.mm.entity.SemesterType.FALL) && (semester.getYear() == startYear);
    }

    private boolean isSecondSemester(ru.urfu.mm.entity.Semester semester, int startYear) {
        return (semester.getType() == ru.urfu.mm.entity.SemesterType.SPRING) && (semester.getYear() == startYear + 1);
    }

    private boolean isThirdSemester(ru.urfu.mm.entity.Semester semester, int startYear) {
        return (semester.getType() == ru.urfu.mm.entity.SemesterType.FALL) && (semester.getYear() == startYear + 1);
    }

    private boolean isFourthSemester(ru.urfu.mm.entity.Semester semester, int startYear) {
        return (semester.getType() == ru.urfu.mm.entity.SemesterType.SPRING) && (semester.getYear() == startYear + 2);
    }
}
