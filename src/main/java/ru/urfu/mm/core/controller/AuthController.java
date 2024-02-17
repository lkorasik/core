package ru.urfu.mm.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.core.dto.*;
import ru.urfu.mm.core.entity.User;
import ru.urfu.mm.core.entity.UserRole;
import ru.urfu.mm.core.service.AuthenticationService;
import ru.urfu.mm.core.service.UserService;

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
    public AccessTokenDto registerAdministration(@RequestBody RegistrationAdministratorDTO user) {
        logger.debug("Request for admin registration: " + user);

        userService.createAdmin(user);
        String token = authenticationService.generateToken(user);

        return new AccessTokenDto(token, user.getRegistrationToken(), UserRole.ADMIN);
    }

    @PostMapping("/registerStudent")
    public AccessTokenDto registerStudent(@RequestBody RegistrationStudentDto user) {
        logger.debug("Request for student registration: " + user);

        userService.createStudent(user);
        String token = authenticationService.generateToken(user);

        return new AccessTokenDto(token, user.getRegistrationToken(), UserRole.STUDENT);
    }

    @PostMapping("/login")
    public AccessTokenDto login(@RequestBody LoginDTO loginDTO) {
        User user = userService.login(loginDTO);
        String token = authenticationService.generateToken(loginDTO);

        return new AccessTokenDto(token, loginDTO.getEmail(), user.getRole());
    }

    @PostMapping("/validateToken")
    public void validateToken(@RequestBody TokenDTO tokenDTO) {
        authenticationService.validateToken(tokenDTO.getToken());
    }
}
