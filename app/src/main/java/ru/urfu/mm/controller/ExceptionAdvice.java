package ru.urfu.mm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.urfu.mm.application.usecase.create.DifferentPasswordException;
import ru.urfu.mm.application.usecase.create.IncorrectUserRoleException;
import ru.urfu.mm.application.usecase.create.RegistrationTokenNotExistException;
import ru.urfu.mm.application.usecase.create.TooShortPasswordException;
import ru.urfu.mm.application.usecase.get_module.ModuleNotFoundException;
import ru.urfu.mm.application.usecase.login_user.InvalidCredentialsException;
import ru.urfu.mm.domain.exception.ApplicationException;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(value = {ApplicationException.class})
    public ResponseEntity<ExceptionDTO> invalidCredentialsException(ApplicationException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDTO(exception.getMessage()));
    }
}
