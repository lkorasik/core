package ru.urfu.mm.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.dto.RegistrationAdministratorDTO;
import ru.urfu.mm.core.entity.User;
import ru.urfu.mm.core.entity.UserRole;
import ru.urfu.mm.core.repository.RegistrationTokenRepository;
import ru.urfu.mm.core.repository.UserRepository;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepository repository;
    @Autowired
    private RegistrationTokenRepository registrationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createAdmin(RegistrationAdministratorDTO dto) {
        UUID registrationToken = UUID.fromString(dto.getRegistrationToken());

        UserRole token = registrationTokenRepository
                .findByRegistrationToken(registrationToken)
                .orElseThrow(() -> new RuntimeException("Error in database"))
                .userRole;
        ensureRole(UserRole.ADMIN, token, dto.getClass().getName(), registrationToken.toString());

        User user = new User(registrationToken, passwordEncoder.encode(dto.getPassword()), UserRole.ADMIN);
        repository.save(user);

        registrationTokenRepository.deleteById(registrationToken);
    }


//    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = repository.findAllByLogin(UUID.fromString(username)).orElseThrow();
        return new org.springframework.security.core.userdetails.User(user.login.toString(), user.password, Collections.emptyList());
    }

    private void ensureRole(UserRole expected, UserRole current, String dtoName, String token) {
        if(current != expected) {
            if(current != null) {
                logger.warn("Registration token " + token + " was used for " + expected + " registration");
            }
            throw new IllegalArgumentException("Registration token " + token + " is invalid " + dtoName);
        }
    }
}