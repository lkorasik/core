package ru.urfu.mm.controller.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.application.usecase.create.user.CreateUser;
import ru.urfu.mm.application.usecase.create.user.CreateUserRequest;
import ru.urfu.mm.application.usecase.loginuser.LoginRequest;
import ru.urfu.mm.application.usecase.loginuser.LoginUser;
import ru.urfu.mm.domain.User;
import ru.urfu.mm.domain.UserRole;
import ru.urfu.mm.service.AuthenticationService;

import java.util.UUID;

@RestController
@RequestMapping("/api/authentication/")
public class AuthenticationController {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;
    private final CreateUser createUser;
    private final LoginUser loginUser;

    @Autowired
    public AuthenticationController(
            AuthenticationService authenticationService,
            CreateUser createUser,
            LoginUser loginUser) {
        this.authenticationService = authenticationService;
        this.createUser = createUser;
        this.loginUser = loginUser;
    }

    @PostMapping("/register")
    public AccessTokenDTO register(@RequestBody RegistrationDTO user) {
        CreateUserRequest request = new CreateUserRequest(
                UUID.fromString(user.token()),
                user.password(),
                user.passwordAgain()
        );
        UserRole role = createUser.createUser(request);
        String token = authenticationService.generateToken(user);

        return new AccessTokenDTO(token, user.token(), role.getValue());
    }

    @PostMapping("/login")
    public AccessTokenDTO login(@RequestBody LoginDTO loginDTO) {
        LoginRequest login = new LoginRequest(UUID.fromString(loginDTO.token()), loginDTO.password());
        User user = loginUser.loginUser(login);
        String token = authenticationService.generateToken(loginDTO);

        return new AccessTokenDTO(token, loginDTO.token(), user.getRole().getValue());
    }

    @PostMapping("/validateToken")
    public void validateToken(@RequestBody TokenDTO tokenDTO) {
        authenticationService.validateToken(tokenDTO.token());
    }
}
