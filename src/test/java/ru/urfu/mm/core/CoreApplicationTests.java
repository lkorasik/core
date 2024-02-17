package ru.urfu.mm.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.urfu.mm.core.entity.RegistrationToken;
import ru.urfu.mm.core.entity.UserRole;
import ru.urfu.mm.core.repository.RegistrationTokenRepository;
import ru.urfu.mm.core.repository.UserRepository;

import java.util.List;
import java.util.UUID;

//@DataJpaTest
//@AutoConfigureTestDatabase
@SpringBootTest
class CoreApplicationTests {
	@Autowired
	public RegistrationTokenRepository registrationTokenRepository;
	@Autowired
	public UserRepository userRepository;

	@Test
	public void init() {
		// Инициализация

		Iterable<RegistrationToken> tokens = List.of(new RegistrationToken[]{
				new RegistrationToken(UUID.randomUUID(), UserRole.STUDENT),
				new RegistrationToken(UUID.randomUUID(), UserRole.UNIVERSITY_EMPLOYEE),
				new RegistrationToken(UUID.randomUUID(), UserRole.ADMIN)
		});
		registrationTokenRepository.saveAll(tokens);
	}

	@Test
	public void drop() {
		registrationTokenRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void recreate() {
		drop();
		init();
	}
}
