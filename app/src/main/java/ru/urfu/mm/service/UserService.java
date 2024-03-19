package ru.urfu.mm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urfu.mm.controller.authentication.LoginDTO;
import ru.urfu.mm.controller.authentication.RegistrationAdministratorDTO;
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
    private final StudentRepository studentRepository;
    private final RegistrationTokenRepository registrationTokenRepository;
    private final EducationalProgramRepository educationalProgramRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(
            UserRepository userRepository,
            StudentRepository studentRepository,
            RegistrationTokenRepository registrationTokenRepository,
            EducationalProgramRepository educationalProgramRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.registrationTokenRepository = registrationTokenRepository;
        this.educationalProgramRepository = educationalProgramRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createAdmin(RegistrationAdministratorDTO dto) {
        UUID registrationToken = UUID.fromString(dto.token());

        UserRole token = registrationTokenRepository
                .findByRegistrationToken(registrationToken)
                .orElseThrow(() -> new RegistrationTokenNotExistException(registrationToken))
                .userRole;
        ensureRole(UserRole.ADMIN, token, registrationToken.toString());

        User user = new User(registrationToken, passwordEncoder.encode(dto.password()), UserRole.ADMIN);
        userRepository.save(user);

        registrationTokenRepository.deleteById(registrationToken);
    }

    public void createStudent(RegistrationStudentDTO dto) {
        UUID registrationToken = UUID.fromString(dto.token());

        UserRole token = registrationTokenRepository
                .findByRegistrationToken(registrationToken)
                .orElseThrow(() -> new RegistrationTokenNotExistException(registrationToken))
                .userRole;
        ensureRole(UserRole.STUDENT, token, registrationToken.toString());

        User user = new User(registrationToken, passwordEncoder.encode(dto.password()), UserRole.STUDENT);
        userRepository.save(user);

        EducationalProgram educationalProgram = educationalProgramRepository
                .getReferenceById(dto.programId());

        Student student = new Student(registrationToken, educationalProgram, dto.group(), user);
        studentRepository.save(student);

        registrationTokenRepository.deleteById(registrationToken);
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

    private void ensureRole(UserRole expected, UserRole current, String token) {
        if(current != expected) {
            if(current != null) {
                logger.warn("Registration token " + token + " was used for " + expected + " registration");
            }
            throw new IncorrectUserRoleException(token);
        }
    }
}