package com.userservice.controllers;

import com.userservice.dtos.LoginRequestDto;
import com.userservice.dtos.LogoutRequestDto;
import com.userservice.dtos.SignUpRequestDto;
import com.userservice.dtos.UserDto;
import com.userservice.exceptions.PasswordNotMatchException;
import com.userservice.exceptions.TokenNotExistException;
import com.userservice.exceptions.UserAlreadyExistException;
import com.userservice.exceptions.UserNotExistException;
import com.userservice.models.Token;
import com.userservice.models.User;
import com.userservice.services.UserService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto) throws UserNotExistException, PasswordNotMatchException {
        return userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }

    @PostMapping("/signUp")
    public  ResponseEntity<User> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws UserAlreadyExistException {
        return new ResponseEntity<>(
                userService.signUp(signUpRequestDto.getName(), signUpRequestDto.getEmail(), signUpRequestDto.getPassword()),
                HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) throws TokenNotExistException {
        userService.logout(logoutRequestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token") @NonNull String token) {
        return UserDto.convertUserToUserDto(userService.validateToken(token));
    }
}
