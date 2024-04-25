package ru.urfu.mm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.entity.EducationalProgram;
import ru.urfu.mm.entity.StudyPlanEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudyPlanRepository extends JpaRepository<StudyPlanEntity, UUID> {
    List<StudyPlanEntity> findAllByProgram(EducationalProgram program);
}
