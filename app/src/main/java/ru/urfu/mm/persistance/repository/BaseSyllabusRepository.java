package ru.urfu.mm.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.persistance.entity.BaseSyllabusEntity;

import java.util.UUID;

@Repository
public interface BaseSyllabusRepository extends JpaRepository<BaseSyllabusEntity, UUID> {
}
