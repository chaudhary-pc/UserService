package com.userservice.exceptions;

import com.userservice.models.User;

public class UserNotExistException extends Exception{
    public UserNotExistException(String message){
        super(message);
    }
}
