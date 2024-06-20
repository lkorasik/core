package ru.urfu.mm.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.persistance.entity.BaseSyllabusEntity;

@Service
public class BaseSyllabusMapper {
    private final BaseSemesterPlanMapper baseSemesterPlanMapper;

    @Autowired
    public BaseSyllabusMapper(BaseSemesterPlanMapper baseSemesterPlanMapper) {
        this.baseSemesterPlanMapper = baseSemesterPlanMapper;
    }

    public BaseSyllabusEntity toEntity(BaseSyllabus syllabus) {
        return new BaseSyllabusEntity(
                syllabus.getId(),
                baseSemesterPlanMapper.toEntity(syllabus.getFirstSemesterPlan()),
                baseSemesterPlanMapper.toEntity(syllabus.getSecondSemesterPlan()),
                baseSemesterPlanMapper.toEntity(syllabus.getThirdSemesterPlan()),
                baseSemesterPlanMapper.toEntity(syllabus.getFourthSemesterPlan())
        );
    }
}
