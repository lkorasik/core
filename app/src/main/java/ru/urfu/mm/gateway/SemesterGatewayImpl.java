package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.SemesterGateway;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.repository.SemesterRepository;

import java.util.List;
import java.util.UUID;

@Component
public class SemesterGatewayImpl implements SemesterGateway {
    private final SemesterRepository semesterRepository;

    @Autowired
    public SemesterGatewayImpl(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @Override
    public Semester getById(UUID semesterId) {
        ru.urfu.mm.entity.Semester entity = semesterRepository.getReferenceById(semesterId);
        return new Semester(
                entity.getId(),
                entity.getYear(),
                entity.getSemesterNumber()
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
                        x.getSemesterNumber()
                ))
                .toList();
    }
}
