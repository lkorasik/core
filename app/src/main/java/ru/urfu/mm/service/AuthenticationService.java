package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.urfu.mm.dto.LoginDTO;
import ru.urfu.mm.dto.RegistrationAdministratorDTO;
import ru.urfu.mm.dto.RegistrationStudentDTO;

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

    public String generateToken(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(loginDTO.getEmail());
        return jwtService.generateToken(userDetails);
    }

    public String generateToken(RegistrationAdministratorDTO registrationAdministratorDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registrationAdministratorDTO.getRegistrationToken(), registrationAdministratorDTO.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(registrationAdministratorDTO.getRegistrationToken());
        return jwtService.generateToken(userDetails);
    }

    public String generateToken(RegistrationStudentDTO registrationStudentDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registrationStudentDto.getRegistrationToken(), registrationStudentDto.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(registrationStudentDto.getRegistrationToken());
        return jwtService.generateToken(userDetails);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}