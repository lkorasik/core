package ru.urfu.mm.controller.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.urfu.mm.application.usecase.create.DifferentPasswordException;
import ru.urfu.mm.application.usecase.create.IncorrectUserRoleException;
import ru.urfu.mm.application.usecase.create.RegistrationTokenNotExistException;
import ru.urfu.mm.application.usecase.create.TooShortPasswordException;
import ru.urfu.mm.application.usecase.create.student.EducationalProgramNotExistsException;
import ru.urfu.mm.application.usecase.loginuser.InvalidCredentialsException;
import ru.urfu.mm.controller.ExceptionDTO;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(value = {InvalidCredentialsException.class, TooShortPasswordException.class,
            RegistrationTokenNotExistException.class, IncorrectUserRoleException.class,
            DifferentPasswordException.class, EducationalProgramNotExistsException.class})
    public ResponseEntity<ExceptionDTO> invalidCredentialsException(RuntimeException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDTO(exception.getMessage()));
    }
}
