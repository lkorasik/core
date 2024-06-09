package ru.urfu.mm.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.persistance.entity.SemesterEntity;

import java.util.UUID;

@Repository
public interface SemesterRepository extends JpaRepository<SemesterEntity, UUID> {
}
