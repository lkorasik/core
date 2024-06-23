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
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Autowired
    public AuthenticationService(
            AccountService accountService,
            AuthenticationManager authenticationManager,
            JWTService jwtService) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String generateToken(LoginDTO dto) {
        return generateToken(String.valueOf(dto.token()), dto.password());
    }

    public String generateToken(RegistrationDTO dto) {
        return generateToken(String.valueOf(dto.token()), dto.password());
    }

    private String generateToken(String principal, String credentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principal, credentials));
        UserDetails userDetails = accountService.loadUserByUsername(principal);
        return jwtService.generateToken(userDetails);
    }
}