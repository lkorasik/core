package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.BaseSemesterPlanGateway;
import ru.urfu.mm.domain.BaseSemesterPlan;
import ru.urfu.mm.persistance.entity.BaseSemesterPlanEntity;
import ru.urfu.mm.persistance.repository.BaseSemesterPlanRepository;
import ru.urfu.mm.service.mapper.BaseSemesterPlanMapper;

@Component
public class BaseSemesterPlanGatewayImpl implements BaseSemesterPlanGateway {
    private final BaseSemesterPlanRepository baseSemesterPlanRepository;
    private final BaseSemesterPlanMapper baseSemesterPlanMapper;

    @Autowired
    public BaseSemesterPlanGatewayImpl(
            BaseSemesterPlanRepository baseSemesterPlanRepository,
            BaseSemesterPlanMapper baseSemesterPlanMapper
    ) {
        this.baseSemesterPlanRepository = baseSemesterPlanRepository;
        this.baseSemesterPlanMapper = baseSemesterPlanMapper;
    }

    @Override
    public void save(BaseSemesterPlan baseSemesterPlan) {
        BaseSemesterPlanEntity entity = baseSemesterPlanMapper.toEntity(baseSemesterPlan);
        baseSemesterPlanRepository.save(entity);
    }
}
