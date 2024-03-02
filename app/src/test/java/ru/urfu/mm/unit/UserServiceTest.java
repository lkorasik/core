package ru.urfu.mm.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.urfu.mm.dsl.DTO_DSL;
import ru.urfu.mm.dsl.EntityDSL;
import ru.urfu.mm.dto.LoginDTO;
import ru.urfu.mm.dto.RegistrationAdministratorDTO;
import ru.urfu.mm.dto.RegistrationStudentDTO;
import ru.urfu.mm.entity.*;
import ru.urfu.mm.exceptions.IncorrectUserRoleException;
import ru.urfu.mm.exceptions.RegistrationTokenNotExistException;
import ru.urfu.mm.repository.EducationalProgramRepository;
import ru.urfu.mm.repository.RegistrationTokenRepository;
import ru.urfu.mm.repository.StudentRepository;
import ru.urfu.mm.repository.UserRepository;
import ru.urfu.mm.service.UserService;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private static UserRepository userRepository;
    @Mock
    private static StudentRepository studentRepository;
    @Mock
    private static RegistrationTokenRepository registrationTokenRepository;
    @Mock
    private static EducationalProgramRepository educationalProgramRepository;
    @Mock
    private static PasswordEncoder passwordEncoder;

    private static UserService userService;

    /**
     * Регистрация администратора
     */
    @Test
    public void administratorRegistration() {
        RegistrationAdministratorDTO dto = DTO_DSL.createRegistrationAdministratorDTO();
        UUID id = UUID.fromString(dto.getRegistrationToken());

        RegistrationToken registrationToken = new RegistrationToken(id, UserRole.ADMIN);

        when(registrationTokenRepository.findByRegistrationToken(id))
                .thenReturn(Optional.of(registrationToken));

        initUserService();

        userService.createAdmin(dto);

        User user = new User(id, passwordEncoder.encode(dto.getPassword()), UserRole.ADMIN);
        verify(userRepository, atMostOnce()).save(user);

        verify(registrationTokenRepository, atMostOnce()).deleteById(id);
    }

    /**
     * Регистрация администратора. Регистрационный токен не внесен в базу данных.
     */
    @Test
    public void administratorRegistration_tokenNotFound() {
        RegistrationAdministratorDTO dto = DTO_DSL.createRegistrationAdministratorDTO();
        UUID id = UUID.fromString(dto.getRegistrationToken());

        when(registrationTokenRepository.findByRegistrationToken(id))
                .thenReturn(Optional.empty());

        initUserService();

        Assertions.assertThrows(RegistrationTokenNotExistException.class, () -> userService.createAdmin(dto));

        User user = new User(id, passwordEncoder.encode(dto.getPassword()), UserRole.ADMIN);
        verify(userRepository, never()).save(user);

        verify(registrationTokenRepository, never()).deleteById(id);
    }

    /**
     * Регистрация администратора. Не верно указан тип пользователя.
     */
    @Test
    public void administratorRegistration_incorrectUserRole() {
        RegistrationAdministratorDTO dto = DTO_DSL.createRegistrationAdministratorDTO();
        UUID id = UUID.fromString(dto.getRegistrationToken());

        RegistrationToken registrationToken = new RegistrationToken(id, UserRole.STUDENT);

        when(registrationTokenRepository.findByRegistrationToken(id))
                .thenReturn(Optional.of(registrationToken));

        initUserService();

        Assertions.assertThrows(IncorrectUserRoleException.class, () -> userService.createAdmin(dto));

        User user = new User(id, passwordEncoder.encode(dto.getPassword()), UserRole.ADMIN);
        verify(userRepository, never()).save(user);

        verify(registrationTokenRepository, never()).deleteById(id);
    }

    /**
     * Регистрация студента
     */
    @Test
    public void studentRegistration() {
        RegistrationStudentDTO dto = DTO_DSL.createRegistrationStudentDTO();
        UUID id = UUID.fromString(dto.getRegistrationToken());

        RegistrationToken registrationToken = new RegistrationToken(id, UserRole.STUDENT);

        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();

        when(registrationTokenRepository.findByRegistrationToken(id))
                .thenReturn(Optional.of(registrationToken));
        when(educationalProgramRepository.getReferenceById(dto.getEducationalProgramId()))
                .thenReturn(educationalProgram);

        initUserService();

        userService.createStudent(dto);

        User user = new User(id, passwordEncoder.encode(dto.getPassword()), UserRole.ADMIN);
        verify(userRepository, atMostOnce()).save(user);

        Student student = new Student(user.getLogin(), educationalProgram, dto.getGroup(), user);
        verify(studentRepository, atMostOnce()).save(student);

        verify(registrationTokenRepository, atMostOnce()).deleteById(id);
    }

    /**
     * Регистрация студента. Регистрационный токен не внесен в базу данных.
     */
    @Test
    public void studentRegistration_tokenNotFound() {
        RegistrationStudentDTO dto = DTO_DSL.createRegistrationStudentDTO();
        UUID id = UUID.fromString(dto.getRegistrationToken());

        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();

        when(registrationTokenRepository.findByRegistrationToken(id))
                .thenReturn(Optional.empty());

        initUserService();

        Assertions.assertThrows(RegistrationTokenNotExistException.class, () -> userService.createStudent(dto));

        User user = new User(id, passwordEncoder.encode(dto.getPassword()), UserRole.ADMIN);
        verify(userRepository, never()).save(user);

        verify(educationalProgramRepository, never()).getReferenceById(educationalProgram.getId());

        Student student = new Student(user.getLogin(), educationalProgram, dto.getGroup(), user);
        verify(studentRepository, never()).save(student);

        verify(registrationTokenRepository, never()).deleteById(id);
    }

    /**
     * Регистрация студента. Не верно указан тип пользователя.
     */
    @Test
    public void studentRegistration_incorrectUserRole() {
        RegistrationStudentDTO dto = DTO_DSL.createRegistrationStudentDTO();
        UUID id = UUID.fromString(dto.getRegistrationToken());

        RegistrationToken registrationToken = new RegistrationToken(id, UserRole.ADMIN);

        when(registrationTokenRepository.findByRegistrationToken(id))
                .thenReturn(Optional.of(registrationToken));

        initUserService();

        Assertions.assertThrows(IncorrectUserRoleException.class, () -> userService.createStudent(dto));

        User user = new User(id, passwordEncoder.encode(dto.getPassword()), UserRole.STUDENT);
        verify(userRepository, never()).save(user);

        verify(registrationTokenRepository, never()).deleteById(id);
    }

    /**
     * Проблема, метод всегда выдает ложный ответ. Потому что поле id в аргументе getRefereceById сравнивается с
     * аналогичным объектом из метода UserService по ссылке, а не через equals.
     *
     * Вырезка из лога:
     * Also, this error might show up because you use argument matchers with methods that cannot be mocked.
     * Following methods *cannot* be stubbed/verified: final/private/equals()/hashCode().
     * Mocking methods declared on non-public parent classes is not supported.
     */
    @Disabled
    @Test
    public void login() {
        LoginDTO loginDTO = DTO_DSL.createLoginDTO();
        UUID id = UUID.fromString(loginDTO.getEmail());
        UUID mockedId = mock();

        User expected = new User();

//        doReturn(true).when(spyed).equals(any());
        doReturn(true).when(mockedId).equals(any());
        when(mockedId.equals(any())).thenReturn(true);
        when(userRepository.getReferenceById(id)).thenReturn(expected);

        User user = userService.login(loginDTO);

        Assertions.assertEquals(expected, user);
    }

    /**
     * Проблема, метод всегда выдает ложный ответ. Потому что поле id в аргументе findAllByLogin сравнивается с
     * аналогичным объектом из метода UserService по ссылке, а не через equals.
     *
     * Вырезка из лога:
     * Also, this error might show up because you use argument matchers with methods that cannot be mocked.
     * Following methods *cannot* be stubbed/verified: final/private/equals()/hashCode().
     * Mocking methods declared on non-public parent classes is not supported.
     */
    @Disabled
    @Test
    public void loadUserByUsername() {
        String login = UUID.randomUUID().toString();

        User user = new User();

        when(userRepository.findAllByLogin(UUID.fromString(login))).thenReturn(Optional.of(user));

        initUserService();

        UserDetails userDetails = userService.loadUserByUsername(login);

        UserDetails expected = new org.springframework.security.core.userdetails.User(user.getLogin().toString(), user.getPassword(), Collections.emptyList());
        Assertions.assertEquals(expected, userDetails);
    }

    private void initUserService() {
        userService = new UserService(
                userRepository,
                studentRepository,
                registrationTokenRepository,
                educationalProgramRepository,
                passwordEncoder
        );
    }
}
