package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.persistance.entity.GroupEntity;
import ru.urfu.mm.persistance.entity.StudentEntity;
import ru.urfu.mm.persistance.entity.StudentRegistrationToken;
import ru.urfu.mm.persistance.entity.AccountEntity;
import ru.urfu.mm.persistance.repository.GroupRepository;
import ru.urfu.mm.persistance.repository.RegistrationTokenRepository;
import ru.urfu.mm.persistance.repository.StudentRegistrationTokenRepository;
import ru.urfu.mm.persistance.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenGatewayImpl implements TokenGateway {
    private final RegistrationTokenRepository registrationTokenRepository;
    private final StudentRegistrationTokenRepository studentRegistrationTokenRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public TokenGatewayImpl(
            RegistrationTokenRepository registrationTokenRepository,
            StudentRegistrationTokenRepository studentRegistrationTokenRepository,
            GroupRepository groupRepository,
            StudentRepository studentRepository) {
        this.registrationTokenRepository = registrationTokenRepository;
        this.studentRegistrationTokenRepository = studentRegistrationTokenRepository;
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void deleteToken(UUID token) {
        registrationTokenRepository.deleteById(token);
    }

    @Override
    public List<UUID> getTokensByGroup(AcademicGroup academicGroup) {
        throw new NotImplementedException();
//        GroupEntity groupEntity = groupRepository.findById(academicGroup.getId()).get();
//        return studentRegistrationTokenRepository.findAllByGroup(groupEntity)
//                .stream()
//                .map(StudentRegistrationToken::getToken)
//                .toList();
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
