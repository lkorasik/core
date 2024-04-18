package ru.urfu.mm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.entity.EducationalProgram;
import ru.urfu.mm.entity.GroupEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends CrudRepository<GroupEntity, UUID> {
    List<GroupEntity> findAllByEducationalProgram(EducationalProgram program);
}
