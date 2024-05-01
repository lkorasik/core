package ru.urfu.mm.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.persistance.entity.EducationalProgramEntity;

import java.util.UUID;

@Repository
public interface ProgramRepository extends JpaRepository<EducationalProgramEntity, UUID> {
}
