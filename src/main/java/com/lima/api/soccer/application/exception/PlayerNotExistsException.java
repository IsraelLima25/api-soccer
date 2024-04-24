package com.lima.api.soccer.application.exception;

public class PlayerNotExistsException extends RuntimeException {

    private String message;

    public PlayerNotExistsException(String message){
        super(message);
    }
}
