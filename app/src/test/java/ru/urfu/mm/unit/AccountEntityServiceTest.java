package ru.urfu.mm.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.urfu.mm.entity.UserEntity;
import ru.urfu.mm.repository.UserRepository;
import ru.urfu.mm.service.UserService;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountEntityServiceTest {
    @Mock
    private static UserRepository userRepository;
    @Mock
    private static PasswordEncoder passwordEncoder;

    /**
     * Проблема, метод всегда выдает ложный ответ. Потому что поле programId в аргументе findAllByLogin сравнивается с
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

        UserEntity userEntity = new UserEntity();

        when(userRepository.findAllByLogin(UUID.fromString(login))).thenReturn(Optional.of(userEntity));

        UserService userService = new UserService(userRepository);

        UserDetails userDetails = userService.loadUserByUsername(login);

        UserDetails expected = new org.springframework.security.core.userdetails.User(userEntity.getLogin().toString(), userEntity.getPassword(), Collections.emptyList());
        Assertions.assertEquals(expected, userDetails);
    }
}
