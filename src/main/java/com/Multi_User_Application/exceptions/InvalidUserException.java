package com.Multi_User_Application.exceptions;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message){
        super(message);
    }
}
