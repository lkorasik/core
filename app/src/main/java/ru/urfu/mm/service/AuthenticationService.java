package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.urfu.mm.controller.authentication.LoginDTO;
import ru.urfu.mm.controller.authentication.RegistrationDTO;

@Service
public class AuthenticationService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Autowired
    public AuthenticationService(
            UserService userService,
            AuthenticationManager authenticationManager,
            JWTService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String generateToken(LoginDTO dto) {
        return generateToken(dto.token(), dto.password());
    }

    public String generateToken(RegistrationDTO dto) {
        return generateToken(dto.token(), dto.password());
    }

    private String generateToken(String principal, String credentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principal, credentials));
        UserDetails userDetails = userService.loadUserByUsername(principal);
        return jwtService.generateToken(userDetails);
    }
}