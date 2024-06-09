package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.SemesterPlanGateway;
import ru.urfu.mm.domain.SemesterPlan;
import ru.urfu.mm.persistance.entity.SemesterEntity;
import ru.urfu.mm.persistance.entity.SemesterPlanEntity;
import ru.urfu.mm.persistance.entity.enums.SemesterType;
import ru.urfu.mm.persistance.repository.SemesterPlanRepository;

@Component
public class SemesterPlanGatewayImpl implements SemesterPlanGateway {
    private final SemesterPlanRepository semesterPlanRepository;

    @Autowired
    public SemesterPlanGatewayImpl(SemesterPlanRepository semesterPlanRepository) {
        this.semesterPlanRepository = semesterPlanRepository;
    }

    @Override
    public void save(SemesterPlan semesterPlan) {
        SemesterPlanEntity entity = new SemesterPlanEntity(
                semesterPlan.getId(),
                new SemesterEntity(
                        semesterPlan.getSemester().getId(),
                        semesterPlan.getSemester().getYear(),
                        SemesterType.fromDomain(semesterPlan.getSemester().getType())
                ),
                semesterPlan.getRecommendedCredits()
        );
        semesterPlanRepository.save(entity);
    }
}
