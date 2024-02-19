package com.userservice.exceptions;

public class TokenNotExistException extends Exception{
    public TokenNotExistException(String message){
        super(message);
    }
}
