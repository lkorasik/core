package ru.urfu.mm.application.usecase.generate_student_registration_token;

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
    private final ProgramGateway programGateway;
    private final StudentGateway studentGateway;

    public GenerateStudentRegistrationToken(GetAcademicGroup getAcademicGroup, ProgramGateway programGateway, StudentGateway studentGateway) {
        this.getAcademicGroup = getAcademicGroup;
        this.programGateway = programGateway;
        this.studentGateway = studentGateway;
    }

    public List<UUID> generateTokens(UUID groupId, int tokensCount) {
        AcademicGroup group = getAcademicGroup.getGroup(groupId);

        if (tokensCount <= 0) {
            throw new IncorrectNumberOfTokensException(tokensCount);
        }

        List<UUID> registrationTokens = Stream.generate(UUID::randomUUID).limit(tokensCount).toList();

        EducationalProgram educationalProgram = programGateway
                .findByGroup(group)
                .orElseThrow(() -> new EducationalProgramNotFoundException(groupId));

        List<Student> students = registrationTokens.stream().map(Student::new).toList();
        studentGateway.saveGroupStudents(students, group);

        return registrationTokens;
    }
}
