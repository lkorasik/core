package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.BaseSemesterPlanGateway;
import ru.urfu.mm.domain.BaseSemesterPlan;
import ru.urfu.mm.persistance.entity.BaseSemesterPlanEntity;
import ru.urfu.mm.persistance.repository.BaseSemesterPlanRepository;
import ru.urfu.mm.service.mapper.SemesterMapper;

@Component
public class BaseSemesterPlanGatewayImpl implements BaseSemesterPlanGateway {
    private final BaseSemesterPlanRepository baseSemesterPlanRepository;
    private final SemesterMapper semesterMapper;

    @Autowired
    public BaseSemesterPlanGatewayImpl(
            BaseSemesterPlanRepository baseSemesterPlanRepository,
            SemesterMapper semesterMapper
    ) {
        this.baseSemesterPlanRepository = baseSemesterPlanRepository;
        this.semesterMapper = semesterMapper;
    }

    @Override
    public void save(BaseSemesterPlan baseSemesterPlan) {
        BaseSemesterPlanEntity entity = new BaseSemesterPlanEntity(
                baseSemesterPlan.getId(),
                semesterMapper.toEntity(baseSemesterPlan.getSemester())
        );
        baseSemesterPlanRepository.save(entity);
    }
}
