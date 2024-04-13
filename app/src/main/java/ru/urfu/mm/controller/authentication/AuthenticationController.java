package ru.urfu.mm.controller.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.application.usecase.CreateAdministrator;
import ru.urfu.mm.application.usecase.CreateStudent;
import ru.urfu.mm.application.usecase.loginuser.LoginRequest;
import ru.urfu.mm.application.usecase.loginuser.LoginUser;
import ru.urfu.mm.domain.User;
import ru.urfu.mm.entity.UserRole;
import ru.urfu.mm.service.AuthenticationService;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/authentication/")
public class AuthenticationController {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;
    private final CreateAdministrator createAdministrator;
    private final CreateStudent createStudent;
    private final LoginUser loginUser;
    private final Mapper<ru.urfu.mm.domain.UserRole, ru.urfu.mm.entity.UserRole> userRoleMapper;

    @Autowired
    public AuthenticationController(
            AuthenticationService authenticationService,
            CreateAdministrator createAdministrator,
            CreateStudent createStudent,
            LoginUser loginUser,
            Mapper<ru.urfu.mm.domain.UserRole, ru.urfu.mm.entity.UserRole> userRoleMapper) {
        this.authenticationService = authenticationService;
        this.createAdministrator = createAdministrator;
        this.createStudent = createStudent;
        this.loginUser = loginUser;
        this.userRoleMapper = userRoleMapper;
    }

    @PostMapping("/registerAdministration")
    public AccessTokenDTO registerAdministration(@RequestBody RegistrationAdministratorDTO user) {
        logger.debug("Request for admin registration: " + user);

        createAdministrator.createAdministrator(UUID.fromString(user.token()), user.password());
        String token = authenticationService.generateToken(user);

        return new AccessTokenDTO(token, user.token(), UserRole.ADMIN);
    }

    @PostMapping("/registerStudent")
    public AccessTokenDTO registerStudent(@RequestBody RegistrationStudentDTO user) {
        logger.debug("Request for student registration: " + user);

        createStudent.createStudent(UUID.fromString(user.token()), user.password(), user.programId(), user.group());
        String token = authenticationService.generateToken(user);

        return new AccessTokenDTO(token, user.token(), UserRole.STUDENT);
    }

    @PostMapping("/login")
    public AccessTokenDTO login(@RequestBody LoginDTO loginDTO) {
        LoginRequest login = new LoginRequest(UUID.fromString(loginDTO.token()), loginDTO.password());
        User user = loginUser.loginUser(login);
        String token = authenticationService.generateToken(loginDTO);

        return new AccessTokenDTO(token, loginDTO.token(), userRoleMapper.map(user.getRole()));
    }

    @PostMapping("/validateToken")
    public void validateToken(@RequestBody TokenDTO tokenDTO) {
        authenticationService.validateToken(tokenDTO.token());
    }
}
