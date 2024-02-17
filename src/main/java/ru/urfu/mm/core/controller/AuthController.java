package ru.urfu.mm.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.core.dto.LoginDTO;
import ru.urfu.mm.core.dto.RegistrationDTO;
import ru.urfu.mm.core.service.AuthenticationService;
import ru.urfu.mm.core.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authentificationService;

    @Autowired
    public AuthController(UserService userService, AuthenticationService authentificationService) {
        this.userService = userService;
        this.authentificationService = authentificationService;
    }

    @PostMapping("/registration")
    public void registration(@RequestBody RegistrationDTO user) {
        var id = userService.createUser(user);
        System.out.println("Ok");
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return authentificationService.generateToken(loginDTO);
    }
}
