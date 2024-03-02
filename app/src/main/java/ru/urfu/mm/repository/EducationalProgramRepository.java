package ru.urfu.mm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.entity.EducationalProgram;

import java.util.UUID;

@Repository
public interface EducationalProgramRepository extends JpaRepository<EducationalProgram, UUID> {
}
