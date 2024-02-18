package ru.urfu.mm.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.core.entity.SpecialCourse;

import java.util.UUID;

@Repository
public interface SpecialCourseRepository extends JpaRepository<SpecialCourse, UUID> {
}
