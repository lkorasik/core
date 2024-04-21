package ru.urfu.mm.gateway;

import org.apache.poi.sl.draw.geom.GuideIf;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.domain.Group;
import ru.urfu.mm.domain.UserRole;
import ru.urfu.mm.entity.GroupEntity;
import ru.urfu.mm.entity.StudentEntity;
import ru.urfu.mm.entity.StudentRegistrationToken;
import ru.urfu.mm.entity.UserEntity;
import ru.urfu.mm.repository.GroupRepository;
import ru.urfu.mm.repository.RegistrationTokenRepository;
import ru.urfu.mm.repository.StudentRegistrationTokenRepository;
import ru.urfu.mm.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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
    public Optional<UserRole> getRoleByToken(UUID token) {
        return registrationTokenRepository
                .findByRegistrationToken(token)
                .map(x -> x.userEntityRole)
                .map(x -> UserRole.values()[x.ordinal()]);
    }

    @Override
    public void deleteToken(UUID token) {
        registrationTokenRepository.deleteById(token);
    }

    @Override
    public List<UUID> getTokensByGroup(Group group) {
        GroupEntity groupEntity = groupRepository.findById(group.getId()).get();
        return studentRegistrationTokenRepository.findAllByGroup(groupEntity)
                .stream()
                .map(StudentRegistrationToken::getToken)
                .toList();
    }

    @Override
    public Optional<Group> getStudentRegistrationToken(UUID token) {
        return studentRegistrationTokenRepository.findById(token)
                .map(StudentRegistrationToken::getGroup)
                .map(x -> new Group(x.getId(), x.getNumber()));
    }

    @Override
    public void deleteStudentRegistrationToken(UUID token) {
        studentRegistrationTokenRepository.deleteById(token);
    }

    @Override
    public boolean isAdministratorToken(UUID token) {
        return registrationTokenRepository.findById(token).isPresent();
    }

    @Override
    public boolean isStudentToken(UUID token) {
        Optional<StudentEntity> maybeStudent = studentRepository.findByLogin(token);
        Optional<UserEntity> maybeUser = maybeStudent.map(StudentEntity::getUser);
        return maybeStudent.isPresent() && maybeUser.isEmpty();
    }
}
