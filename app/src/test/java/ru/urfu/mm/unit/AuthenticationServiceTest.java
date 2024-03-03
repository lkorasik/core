package ru.urfu.mm.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.urfu.mm.dsl.DTO_DSL;
import ru.urfu.mm.dto.LoginDTO;
import ru.urfu.mm.dto.RegistrationAdministratorDTO;
import ru.urfu.mm.dto.RegistrationStudentDTO;
import ru.urfu.mm.service.AuthenticationService;
import ru.urfu.mm.service.JWTService;
import ru.urfu.mm.service.UserService;

import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTService jwtService;

    /**
     * Авторизовываемся в системе через {@link LoginDTO}
     */
    @Test
    public void generateJWT_login() {
        LoginDTO loginDTO = DTO_DSL.createLoginDTO();

        UserDetails userDetails = new User(loginDTO.getEmail(), loginDTO.getPassword(), Collections.emptyList());

        String expectedJWT = "ghfdlsj";

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        when(authenticationManager.authenticate(token)).thenReturn(null);
        when(userService.loadUserByUsername(loginDTO.getEmail())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(expectedJWT);

        AuthenticationService authenticationService = new AuthenticationService(
                userService,
                authenticationManager,
                jwtService
        );

        String actualJWT = authenticationService.generateToken(loginDTO);

        Assertions.assertEquals(expectedJWT, actualJWT);
    }

    /**
     * Авторизовываемся в системе через {@link LoginDTO}. Ошибка во время авторизации.
     */
    @Test
    public void generateJWT_login_authorizationException() {
        LoginDTO loginDTO = DTO_DSL.createLoginDTO();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        when(authenticationManager.authenticate(token)).thenThrow(BadCredentialsException.class);

        AuthenticationService authenticationService = new AuthenticationService(
                userService,
                authenticationManager,
                jwtService
        );

        Assertions.assertThrows(BadCredentialsException.class, () -> authenticationService.generateToken(loginDTO));
    }

    /**
     * Авторизовываемся в системе через {@link RegistrationAdministratorDTO}
     */
    @Test
    public void generateJWT_registrationAdministrator() {
        RegistrationAdministratorDTO registrationAdministratorDTO = DTO_DSL.createRegistrationAdministratorDTO();

        UserDetails userDetails = new User(
                registrationAdministratorDTO.getRegistrationToken(),
                registrationAdministratorDTO.getPassword(),
                Collections.emptyList()
        );

        String expectedJWT = "ghfdlsj";

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                registrationAdministratorDTO.getRegistrationToken(),
                registrationAdministratorDTO.getPassword()
        );

        when(authenticationManager.authenticate(token)).thenReturn(null);
        when(userService.loadUserByUsername(registrationAdministratorDTO.getRegistrationToken()))
                .thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(expectedJWT);

        AuthenticationService authenticationService = new AuthenticationService(
                userService,
                authenticationManager,
                jwtService
        );

        String actualJWT = authenticationService.generateToken(registrationAdministratorDTO);

        Assertions.assertEquals(expectedJWT, actualJWT);
    }

    /**
     * Авторизовываемся в системе через {@link RegistrationAdministratorDTO}. Ошибка во время авторизации.
     */
    @Test
    public void generateJWT_registrationAdministrator_authorizationException() {
        RegistrationAdministratorDTO registrationAdministratorDTO = DTO_DSL.createRegistrationAdministratorDTO();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                registrationAdministratorDTO.getRegistrationToken(),
                registrationAdministratorDTO.getPassword()
        );

        when(authenticationManager.authenticate(token)).thenThrow(BadCredentialsException.class);

        AuthenticationService authenticationService = new AuthenticationService(
                userService,
                authenticationManager,
                jwtService
        );

        Assertions.assertThrows(
                BadCredentialsException.class,
                () -> authenticationService.generateToken(registrationAdministratorDTO)
        );
    }

    /**
     * Авторизовываемся в системе через {@link RegistrationStudentDTO}
     */
    @Test
    public void generateJWT_registrationStudent() {
        RegistrationStudentDTO registrationStudentDTO = DTO_DSL.createRegistrationStudentDTO();

        UserDetails userDetails = new User(
                registrationStudentDTO.getRegistrationToken(),
                registrationStudentDTO.getPassword(),
                Collections.emptyList()
        );

        String expectedJWT = "ghfdlsj";

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                registrationStudentDTO.getRegistrationToken(),
                registrationStudentDTO.getPassword()
        );

        when(authenticationManager.authenticate(token)).thenReturn(null);
        when(userService.loadUserByUsername(registrationStudentDTO.getRegistrationToken()))
                .thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(expectedJWT);

        AuthenticationService authenticationService = new AuthenticationService(
                userService,
                authenticationManager,
                jwtService
        );

        String actualJWT = authenticationService.generateToken(registrationStudentDTO);

        Assertions.assertEquals(expectedJWT, actualJWT);
    }

    /**
     * Авторизовываемся в системе через {@link RegistrationStudentDTO}. Ошибка во время авторизации.
     */
    @Test
    public void generateJWT_registrationStudent_authorizationException() {
        RegistrationStudentDTO registrationStudentDTO = DTO_DSL.createRegistrationStudentDTO();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                registrationStudentDTO.getRegistrationToken(),
                registrationStudentDTO.getPassword()
        );

        when(authenticationManager.authenticate(token)).thenThrow(BadCredentialsException.class);

        AuthenticationService authenticationService = new AuthenticationService(
                userService,
                authenticationManager,
                jwtService
        );

        Assertions.assertThrows(
                BadCredentialsException.class,
                () -> authenticationService.generateToken(registrationStudentDTO)
        );
    }
}
