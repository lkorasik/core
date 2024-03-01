package ru.urfu.mm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.dto.*;
import ru.urfu.mm.entity.User;
import ru.urfu.mm.entity.UserRole;
import ru.urfu.mm.service.AuthenticationService;
import ru.urfu.mm.service.UserService;

@RestController
@RequestMapping("/api/authentication/")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registerAdministration")
    public AccessTokenDTO registerAdministration(@RequestBody RegistrationAdministratorDTO user) {
        logger.debug("Request for admin registration: " + user);

        userService.createAdmin(user);
        String token = authenticationService.generateToken(user);

        return new AccessTokenDTO(token, user.getRegistrationToken(), UserRole.ADMIN);
    }

    @PostMapping("/registerStudent")
    public AccessTokenDTO registerStudent(@RequestBody RegistrationStudentDTO user) {
        logger.debug("Request for student registration: " + user);

        userService.createStudent(user);
        String token = authenticationService.generateToken(user);

        return new AccessTokenDTO(token, user.getRegistrationToken(), UserRole.STUDENT);
    }

    @PostMapping("/login")
    public AccessTokenDTO login(@RequestBody LoginDTO loginDTO) {
        User user = userService.login(loginDTO);
        String token = authenticationService.generateToken(loginDTO);

        return new AccessTokenDTO(token, loginDTO.getEmail(), user.getRole());
    }

    @PostMapping("/validateToken")
    public void validateToken(@RequestBody TokenDTO tokenDTO) {
        authenticationService.validateToken(tokenDTO.getToken());
    }
}
