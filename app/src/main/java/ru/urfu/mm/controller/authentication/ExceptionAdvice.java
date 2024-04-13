package ru.urfu.mm.controller.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.urfu.mm.application.usecase.loginuser.InvalidCredentialsException;
import ru.urfu.mm.controller.ExceptionDTO;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionDTO> invalidCredentialsException(InvalidCredentialsException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDTO(exception.getMessage()));
    }
}
