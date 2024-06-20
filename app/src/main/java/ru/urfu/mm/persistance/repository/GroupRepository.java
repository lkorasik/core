package ru.urfu.mm.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.persistance.entity.EducationalProgramEntity;
import ru.urfu.mm.persistance.entity.GroupEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends CrudRepository<GroupEntity, UUID> {
}
