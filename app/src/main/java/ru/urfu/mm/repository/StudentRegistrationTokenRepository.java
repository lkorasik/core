package ru.urfu.mm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.mm.entity.StudentRegistrationToken;

import java.util.UUID;

@Repository
public interface StudentRegistrationTokenRepository extends JpaRepository<StudentRegistrationToken, UUID> {
}
