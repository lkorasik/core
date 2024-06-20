package ru.urfu.mm.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.domain.BaseSemesterPlan;
import ru.urfu.mm.persistance.entity.BaseSemesterPlanEntity;

@Service
public class BaseSemesterPlanMapper {
    private final SemesterMapper semesterMapper;
    private final CourseMapper courseMapper;

    @Autowired
    public BaseSemesterPlanMapper(SemesterMapper semesterMapper, CourseMapper courseMapper) {
        this.semesterMapper = semesterMapper;
        this.courseMapper = courseMapper;
    }

    public BaseSemesterPlanEntity toEntity(BaseSemesterPlan baseSemesterPlan) {
        return new BaseSemesterPlanEntity(
                baseSemesterPlan.getId(),
                semesterMapper.toEntity(baseSemesterPlan.getSemester())
        );
    }

    public BaseSemesterPlan toDomain(BaseSemesterPlanEntity entity) {
        return new BaseSemesterPlan(
                entity.getId(),
                semesterMapper.toDomain(entity.getSemesterEntity()),
                entity.getRequiredCourses()
                        .stream()
                        .map(courseMapper::toDomain)
                        .toList(),
                entity.getAvailableCourses()
                        .stream()
                        .map(courseMapper::toDomain)
                        .toList(),
                entity.getScienceWorks()
                        .stream()
                        .map(courseMapper::toDomain)
                        .toList()
        );
    }
}
