package ru.urfu.mm.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.persistance.entity.GroupEntity;
import ru.urfu.mm.persistance.entity.StudentRegistrationToken;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRegistrationTokenRepository extends JpaRepository<StudentRegistrationToken, UUID> {
}
