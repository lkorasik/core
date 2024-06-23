package ru.urfu.mm.application.usecase.generate_student_registration_token;

import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.gateway.GroupGateway;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.application.usecase.get_group.GetAcademicGroup;
import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.Student;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Сгенерировать токены для студентов.
 * 1. Находим группу {@link GetAcademicGroup}
 * 2. Если число запрошенных токенов равно нулю или меньше нуля, то кидаем ошибку.
 * 3. Генерируем нужное число токенов
 * 4. Находим программу. Если такой нет, то кидаем ошибку.
 * 4. Создаем частично заполненных студентов.
 */
public class GenerateStudentRegistrationToken {
    private final GetAcademicGroup getAcademicGroup;
    private final StudentGateway studentGateway;
    private final GroupGateway groupGateway;

    public GenerateStudentRegistrationToken(
            GetAcademicGroup getAcademicGroup,
            StudentGateway studentGateway,
            GroupGateway groupGateway
    ) {
        this.getAcademicGroup = getAcademicGroup;
        this.studentGateway = studentGateway;
        this.groupGateway = groupGateway;
    }

    public List<UUID> generateTokens(UUID groupId, int tokensCount) {
        AcademicGroup group = getAcademicGroup.getGroup(groupId);

        if (tokensCount <= 0) {
            throw new IncorrectNumberOfTokensException(tokensCount);
        }

        List<UUID> registrationTokens = Stream.generate(UUID::randomUUID).limit(tokensCount).toList();

        List<Student> students = registrationTokens.stream().map(Student::new).toList();
        AcademicGroup newGroup = new AcademicGroup(
                group.getId(),
                group.getNumber(),
                group.getYear(),
                students,
                group.getBaseSyllabus()
        );

        students.forEach(studentGateway::save);
        groupGateway.save(newGroup);

        return registrationTokens;
    }
}
