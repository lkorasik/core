package ru.urfu.mm.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.domain.BaseSemesterPlan;
import ru.urfu.mm.persistance.entity.BaseSemesterPlanEntity;

@Service
public class BaseSemesterPlanMapper {
    private final SemesterMapper semesterMapper;

    @Autowired
    public BaseSemesterPlanMapper(SemesterMapper semesterMapper) {
        this.semesterMapper = semesterMapper;
    }

    public BaseSemesterPlanEntity toEntity(BaseSemesterPlan baseSemesterPlan) {
        return new BaseSemesterPlanEntity(
                baseSemesterPlan.getId(),
                semesterMapper.toEntity(baseSemesterPlan.getSemester())
        );
    }
}
