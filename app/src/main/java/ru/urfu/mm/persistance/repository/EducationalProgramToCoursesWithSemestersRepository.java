package ru.urfu.mm.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.persistance.entity.EducationalProgramToCoursesWithSemesters;

import java.util.List;
import java.util.UUID;

@Repository
public interface EducationalProgramToCoursesWithSemestersRepository extends JpaRepository<EducationalProgramToCoursesWithSemesters, UUID> {
    List<EducationalProgramToCoursesWithSemesters> findAllByEducationalProgramId(UUID id);
}
