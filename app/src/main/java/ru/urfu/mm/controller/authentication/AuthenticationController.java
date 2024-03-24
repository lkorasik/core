package ru.urfu.mm.controller.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.applicationlegacy.usecase.CreateAdministrator;
import ru.urfu.mm.entity.User;
import ru.urfu.mm.entity.UserRole;
import ru.urfu.mm.service.AuthenticationService;
import ru.urfu.mm.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/authentication/")
public class AuthenticationController {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final CreateAdministrator createAdministrator;

    @Autowired
    public AuthenticationController(
            UserService userService,
            AuthenticationService authenticationService,
            CreateAdministrator createAdministrator) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.createAdministrator = createAdministrator;
    }

    @PostMapping("/registerAdministration")
    public AccessTokenDTO registerAdministration(@RequestBody RegistrationAdministratorDTO user) {
        logger.debug("Request for admin registration: " + user);

        createAdministrator.createAdministrator(UUID.fromString(user.token()), user.password());

//        userService.createAdmin(user);
        String token = authenticationService.generateToken(user);

        return new AccessTokenDTO(token, user.token(), UserRole.ADMIN);
    }

    @PostMapping("/registerStudent")
    public AccessTokenDTO registerStudent(@RequestBody RegistrationStudentDTO user) {
        logger.debug("Request for student registration: " + user);

        userService.createStudent(user);
        String token = authenticationService.generateToken(user);

        return new AccessTokenDTO(token, user.token(), UserRole.STUDENT);
    }

    @PostMapping("/login")
    public AccessTokenDTO login(@RequestBody LoginDTO loginDTO) {
        User user = userService.login(loginDTO);
        String token = authenticationService.generateToken(loginDTO);

        return new AccessTokenDTO(token, loginDTO.token(), user.getRole());
    }

    @PostMapping("/validateToken")
    public void validateToken(@RequestBody TokenDTO tokenDTO) {
        authenticationService.validateToken(tokenDTO.token());
    }
}
