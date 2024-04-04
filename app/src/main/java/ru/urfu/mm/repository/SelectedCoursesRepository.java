package ru.urfu.mm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.entity.SelectedCourses;

import java.util.UUID;

@Repository
public interface SelectedCoursesRepository extends JpaRepository<SelectedCourses, UUID> {
}
