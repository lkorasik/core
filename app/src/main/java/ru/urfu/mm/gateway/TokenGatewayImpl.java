package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.persistance.entity.StudentEntity;
import ru.urfu.mm.persistance.entity.AccountEntity;
import ru.urfu.mm.persistance.repository.RegistrationTokenRepository;
import ru.urfu.mm.persistance.repository.StudentRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class TokenGatewayImpl implements TokenGateway {
    private final RegistrationTokenRepository registrationTokenRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public TokenGatewayImpl(
            RegistrationTokenRepository registrationTokenRepository,
            StudentRepository studentRepository) {
        this.registrationTokenRepository = registrationTokenRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void deleteToken(UUID token) {
        registrationTokenRepository.deleteById(token);
    }

    @Override
    public boolean isAdministratorToken(UUID token) {
        return registrationTokenRepository.findById(token).isPresent();
    }

    @Override
    public boolean isStudentToken(UUID token) {
        Optional<StudentEntity> maybeStudent = studentRepository.findById(token);
        Optional<AccountEntity> maybeUser = maybeStudent.map(StudentEntity::getUser);
        return maybeStudent.isPresent() && maybeUser.isEmpty();
    }
}
