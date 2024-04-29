//package ru.urfu.mm.application.test;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.urfu.mm.application.dsl.DSL;
//import ru.urfu.mm.application.gateway.PasswordGateway;
//import ru.urfu.mm.application.gateway.ProgramGateway;
//import ru.urfu.mm.application.gateway.StudentGateway;
//import ru.urfu.mm.application.gateway.UserGateway;
//import ru.urfu.mm.application.usecase.create.RegistrationTokenNotExistException;
//import ru.urfu.mm.application.usecase.create.account.CreateUserRequest;
//import ru.urfu.mm.domain.AcademicGroup;
//import ru.urfu.mm.domain.EducationalProgram;
//import ru.urfu.mm.domain.Student;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@ExtendWith(MockitoExtension.class)
//public class CreateStudentTest {
//    @Mock
//    private PasswordGateway passwordGateway;
//    @Mock
//    private UserGateway userGateway;
//    @Mock
//    private ProgramGateway programGateway;
//    @Mock
//    private StudentGateway studentGateway;
//
//    /**
//     * Создание аккаунта студента.
//     */
//    @Test
//    public void createStudent() {
//        UUID token = UUID.randomUUID();
//        String password = DSL.generateStrongPassword();
//
//        EducationalProgram educationalProgram = new EducationalProgram(UUID.randomUUID(), DSL.generateString(), DSL.generateString());
//        AcademicGroup academicGroup = new AcademicGroup(UUID.randomUUID(), "МЕНМ-100000");
//        Student student = new Student(token, educationalProgram, academicGroup);
//
//        Mockito.when(studentGateway.findById(token)).thenReturn(Optional.of(student));
//
//        CreateStudent createStudent = new CreateStudent(passwordGateway, userGateway, studentGateway);
//
//        CreateUserRequest request = new CreateUserRequest(token, password, password);
//        createStudent.create(request);
//    }
//
//    /**
//     * Создание аккаунта студента. Студент не найден.
//     */
//    @Test
//    public void createStudent_noRegistrationToken() {
//        UUID token = UUID.randomUUID();
//        String password = DSL.generateStrongPassword();
//
//        Mockito.when(studentGateway.findById(token)).thenReturn(Optional.empty());
//
//        CreateStudent createStudent = new CreateStudent(passwordGateway, userGateway, studentGateway);
//
//        CreateUserRequest request = new CreateUserRequest(token, password, password);
//        Assertions.assertThrows(RegistrationTokenNotExistException.class, () -> createStudent.create(request));
//    }
//}
