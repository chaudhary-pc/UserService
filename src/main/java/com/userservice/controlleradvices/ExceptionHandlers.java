package com.userservice.controlleradvices;

import com.userservice.dtos.ExceptionDto;
import com.userservice.exceptions.PasswordNotMatchException;
import com.userservice.exceptions.TokenNotExistException;
import com.userservice.exceptions.UserAlreadyExistException;
import com.userservice.exceptions.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ExceptionDto> handleUserAlreadyExistException(UserAlreadyExistException userAlreadyExistException){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setName(userAlreadyExistException.getMessage());
        exceptionDto.setDetails("Use a different email address for signUp...");
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ExceptionDto> handleUserNotExistException(UserNotExistException userNotExistException){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setName(userNotExistException.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ExceptionDto> handlePasswordNotMatchException(PasswordNotMatchException passwordNotMatchException){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setName(passwordNotMatchException.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(TokenNotExistException.class)
    public ResponseEntity<ExceptionDto> handleTokenNotExistException(TokenNotExistException tokenNotExistException){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setName(tokenNotExistException.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
