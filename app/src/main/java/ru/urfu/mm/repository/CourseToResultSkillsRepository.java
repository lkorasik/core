package ru.urfu.mm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.entity.CourseToResultSkills;

import java.util.UUID;

@Repository
public interface CourseToResultSkillsRepository extends JpaRepository<CourseToResultSkills, UUID> {
}
