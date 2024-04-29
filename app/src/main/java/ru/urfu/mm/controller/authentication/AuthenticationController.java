package ru.urfu.mm.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.create.account.CreateAccount;
import ru.urfu.mm.application.usecase.login_user.LoginUser;
import ru.urfu.mm.domain.Account;
import ru.urfu.mm.domain.enums.UserRole;
import ru.urfu.mm.service.AuthenticationService;

@RestController
@RequestMapping("/api/authentication/")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final CreateAccount createAccount;
    private final LoginUser loginUser;

    @Autowired
    public AuthenticationController(
            AuthenticationService authenticationService,
            CreateAccount createAccount,
            LoginUser loginUser) {
        this.authenticationService = authenticationService;
        this.createAccount = createAccount;
        this.loginUser = loginUser;
    }

    @PostMapping("/register")
    public AccessTokenDTO register(@RequestBody RegistrationDTO dto) {
        UserRole role = createAccount.createUser(dto.toRequest());
        String token = authenticationService.generateToken(dto);

        return new AccessTokenDTO(token, dto.token(), role.getValue());
    }

    @PostMapping("/login")
    public AccessTokenDTO login(@RequestBody LoginDTO dto) {
        Account account = loginUser.loginUser(dto.toRequest());
        String token = authenticationService.generateToken(dto);

        return new AccessTokenDTO(token, dto.token(), account.role().getValue());
    }

    @PostMapping("/validateToken")
    public void validateToken(@RequestBody TokenDTO tokenDTO) {
        authenticationService.validateToken(tokenDTO.token());
    }
}
