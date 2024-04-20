package ru.urfu.mm.application.usecase.generatetoken;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.usecase.create.student.EducationalProgramNotExistsException;
import ru.urfu.mm.application.usecase.getgroup.GetGroup;
import ru.urfu.mm.domain.Group;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.Student;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Сгенерировать токены для студентов.
 * 1. Находим группу {@link GetGroup}
 * 2. Если число запрошенных токенов равно нулю или меньше нуля, то кидаем ошибку.
 * 3. Генерируем нужное число токенов
 * 4. Находим программу. Если такой нет, то кидаем ошибку.
 * 4. Создаем частично заполненных студентов.
 */
public class GenerateStudentRegistrationTokens {
    private final TokenGateway tokenGateway;
    private final GetGroup getGroup;
    private final ProgramGateway programGateway;
    private final StudentGateway studentGateway;

    public GenerateStudentRegistrationTokens(
            TokenGateway tokenGateway,
            GetGroup getGroup,
            ProgramGateway programGateway,
            StudentGateway studentGateway) {
        this.tokenGateway = tokenGateway;
        this.getGroup = getGroup;
        this.programGateway = programGateway;
        this.studentGateway = studentGateway;
    }

    public List<UUID> generateTokens(GenerateStudentRegistrationTokensRequest request) {
        Group group = getGroup.getGroup(request.groupId());

        if (request.tokenCount() <= 0) {
            throw new IncorrectCountOfTokens(request.tokenCount());
        }

        Program program = programGateway
                .findByGroup(group)
                .orElseThrow(() -> new EducationalProgramNotExistsException(group));

        List<UUID> registrationTokens = Stream.generate(UUID::randomUUID).limit(request.tokenCount()).toList();

        List<Student> students = registrationTokens.stream()
                .map(x -> new Student(x, program, group.getNumber()))
                .toList();
        students.forEach(studentGateway::saveNewStudent);

        return registrationTokens;
    }
}
