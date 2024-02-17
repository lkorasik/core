package ru.urfu.mm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.dto.LoginDTO;
import ru.urfu.mm.core.dto.RegistrationAdministratorDTO;

@Service
public class AuthenticationService {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

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

    public void validateToken(String token) {
        jwtService.validateToken(token);
        System.out.println("Everything okay");
    }
}