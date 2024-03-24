package ru.urfu.mm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urfu.mm.controller.authentication.LoginDTO;
import ru.urfu.mm.controller.authentication.RegistrationStudentDTO;
import ru.urfu.mm.entity.EducationalProgram;
import ru.urfu.mm.entity.Student;
import ru.urfu.mm.entity.User;
import ru.urfu.mm.entity.UserRole;
import ru.urfu.mm.exceptions.IncorrectUserRoleException;
import ru.urfu.mm.exceptions.RegistrationTokenNotExistException;
import ru.urfu.mm.repository.EducationalProgramRepository;
import ru.urfu.mm.repository.RegistrationTokenRepository;
import ru.urfu.mm.repository.StudentRepository;
import ru.urfu.mm.repository.UserRepository;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(LoginDTO loginDTO) {
        UUID uuid = UUID.fromString(loginDTO.token());
        User user = userRepository.getReferenceById(uuid);

        // todo: в чем прикол getReferenceById?
        if (!passwordEncoder.matches(loginDTO.password(), user.getPassword())){
            throw new RuntimeException("Bad credentials");
        }

        return user;
    }

//    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findAllByLogin(UUID.fromString(username)).orElseThrow();
        return new org.springframework.security.core.userdetails.User(user.getLogin().toString(), user.getPassword(), Collections.emptyList());
    }
}