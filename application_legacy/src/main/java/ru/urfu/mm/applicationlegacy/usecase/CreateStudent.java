package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.exception.IncorrectUserRoleException;
import ru.urfu.mm.applicationlegacy.exception.RegistrationTokenNotExistException;
import ru.urfu.mm.applicationlegacy.gateway.*;
import ru.urfu.mm.domainlegacy.EducationalProgram;
import ru.urfu.mm.domainlegacy.Student;
import ru.urfu.mm.domainlegacy.User;
import ru.urfu.mm.domainlegacy.UserRole;

import java.util.UUID;

public class CreateStudent {
    private final TokenGateway tokenGateway;
    private final LoggerGateway loggerGateway;
    private final PasswordGateway passwordGateway;
    private final UserGateway userGateway;
    private final ProgramGateway programGateway;
    private final StudentGateway studentGateway;

    public CreateStudent(
            TokenGateway tokenGateway,
            LoggerGateway loggerGateway,
            PasswordGateway passwordGateway,
            UserGateway userGateway,
            ProgramGateway programGateway,
            StudentGateway studentGateway) {
        this.tokenGateway = tokenGateway;
        this.loggerGateway = loggerGateway;
        this.passwordGateway = passwordGateway;
        this.userGateway = userGateway;
        this.programGateway = programGateway;
        this.studentGateway = studentGateway;
    }

    public void createStudent(UUID token, String password, UUID programId, String group) {
        UserRole role = tokenGateway
                .getRoleByToken(token)
                .orElseThrow(() -> new RegistrationTokenNotExistException(token));
        ensureRole(role, token.toString());

        User user = new User(token, passwordGateway.encode(password), role);
        userGateway.save(user);

        EducationalProgram educationalProgram = programGateway.getById(programId);

        Student student = new Student(token, educationalProgram, group, user);
        studentGateway.save(student);

        tokenGateway.deleteToken(token);
    }

    private void ensureRole(UserRole current, String token) {
        if(current != UserRole.STUDENT) {
            if(current != null) {
                loggerGateway.warn("Registration token " + token + " was used for " + UserRole.STUDENT + " registration");
            }
            throw new IncorrectUserRoleException(token);
        }
    }
}
