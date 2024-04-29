package ru.urfu.mm.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.persistance.entity.GroupEntity;
import ru.urfu.mm.persistance.entity.StudentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
    List<StudentEntity> findAllByGroup(GroupEntity group);
}
