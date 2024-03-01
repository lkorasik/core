package ru.urfu.mm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urfu.mm.dto.LoginDTO;
import ru.urfu.mm.dto.RegistrationAdministratorDTO;
import ru.urfu.mm.dto.RegistrationStudentDTO;
import ru.urfu.mm.entity.EducationalProgram;
import ru.urfu.mm.entity.Student;
import ru.urfu.mm.entity.User;
import ru.urfu.mm.entity.UserRole;
import ru.urfu.mm.repository.EducationalProgramRepository;
import ru.urfu.mm.repository.RegistrationTokenRepository;
import ru.urfu.mm.repository.StudentRepository;
import ru.urfu.mm.repository.UserRepository;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RegistrationTokenRepository registrationTokenRepository;
    @Autowired
    private EducationalProgramRepository educationalProgramRepository;

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
        userRepository.save(user);

        registrationTokenRepository.deleteById(registrationToken);
    }

    public void createStudent(RegistrationStudentDTO dto) {
        UUID registrationToken = UUID.fromString(dto.getRegistrationToken());

        UserRole token = registrationTokenRepository
                .findByRegistrationToken(registrationToken)
                .orElseThrow(() -> new RuntimeException("Error in database"))
                .userRole;
        ensureRole(UserRole.STUDENT, token, dto.getClass().getName(), registrationToken.toString());

        User user = new User(registrationToken, passwordEncoder.encode(dto.getPassword()), UserRole.STUDENT);
        userRepository.save(user);

        EducationalProgram educationalProgram = educationalProgramRepository.getReferenceById(dto.getEducationalProgramId());

        Student student = new Student(registrationToken, educationalProgram, dto.getGroup(), user);
        studentRepository.save(student);

        // todo: надо доделать часть, которая отвечает за регистрацию студента
        registrationTokenRepository.deleteById(registrationToken);
    }

    public User login(LoginDTO loginDTO) {
        UUID uuid = UUID.fromString(loginDTO.getEmail());
        User user = userRepository.getReferenceById(uuid);

        // todo: в чем прикол getReferenceById?
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new RuntimeException("Bad credentials");
        }

        return user;
    }

//    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findAllByLogin(UUID.fromString(username)).orElseThrow();
        return new org.springframework.security.core.userdetails.User(user.getLogin().toString(), user.getPassword(), Collections.emptyList());
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