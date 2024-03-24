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
import ru.urfu.mm.controller.authentication.LoginDTO;
import ru.urfu.mm.controller.authentication.RegistrationAdministratorDTO;
import ru.urfu.mm.controller.authentication.RegistrationStudentDTO;
import ru.urfu.mm.entity.*;
import ru.urfu.mm.exceptions.IncorrectUserRoleException;
import ru.urfu.mm.exceptions.RegistrationTokenNotExistException;
import ru.urfu.mm.repository.EducationalProgramRepository;
import ru.urfu.mm.repository.RegistrationTokenRepository;
import ru.urfu.mm.repository.StudentRepository;
import ru.urfu.mm.repository.UserRepository;
import ru.urfu.mm.service.UserService;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private static UserRepository userRepository;
    @Mock
    private static PasswordEncoder passwordEncoder;

    private static UserService userService;

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
        UUID id = UUID.fromString(loginDTO.token());
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
                passwordEncoder
        );
    }
}
