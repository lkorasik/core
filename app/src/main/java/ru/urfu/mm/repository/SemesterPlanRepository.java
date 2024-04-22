package ru.urfu.mm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.entity.SemesterPlanEntity;

import java.util.UUID;

@Repository
public interface SemesterPlanRepository extends JpaRepository<SemesterPlanEntity, UUID> {
}
