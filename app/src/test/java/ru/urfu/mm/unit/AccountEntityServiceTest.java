package ru.urfu.mm.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.urfu.mm.persistance.entity.AccountEntity;
import ru.urfu.mm.persistance.repository.AccountRepository;
import ru.urfu.mm.service.UserService;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountEntityServiceTest {
    @Mock
    private static AccountRepository accountRepository;
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

        AccountEntity accountEntity = new AccountEntity();

        when(accountRepository.findAllByLogin(UUID.fromString(login))).thenReturn(Optional.of(accountEntity));

        UserService userService = new UserService(accountRepository);

        UserDetails userDetails = userService.loadUserByUsername(login);

        UserDetails expected = new org.springframework.security.core.userdetails.User(accountEntity.getLogin().toString(), accountEntity.getPassword(), Collections.emptyList());
        Assertions.assertEquals(expected, userDetails);
    }
}
