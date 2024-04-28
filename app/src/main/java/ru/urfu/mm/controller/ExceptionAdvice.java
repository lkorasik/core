package ru.urfu.mm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.urfu.mm.application.usecase.create.DifferentPasswordException;
import ru.urfu.mm.application.usecase.create.IncorrectUserRoleException;
import ru.urfu.mm.application.usecase.create.RegistrationTokenNotExistException;
import ru.urfu.mm.application.usecase.create.TooShortPasswordException;
import ru.urfu.mm.application.usecase.generate_token.EducationalProgramNotExistsException;
import ru.urfu.mm.application.usecase.get_module.ModuleNotFoundException;
import ru.urfu.mm.application.usecase.login_user.InvalidCredentialsException;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(value = {InvalidCredentialsException.class, TooShortPasswordException.class,
            RegistrationTokenNotExistException.class, IncorrectUserRoleException.class,
            DifferentPasswordException.class, EducationalProgramNotExistsException.class,
            ModuleNotFoundException.class})
    public ResponseEntity<ExceptionDTO> invalidCredentialsException(RuntimeException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDTO(exception.getMessage()));
    }
}
