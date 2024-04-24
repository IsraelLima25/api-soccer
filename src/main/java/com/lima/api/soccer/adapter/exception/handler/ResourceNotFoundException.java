package com.lima.api.soccer.adapter.exception.handler;

public class ResourceNotFoundException extends RuntimeException {

    private String message;

    public ResourceNotFoundException(String message){
        super(message);
    }
}
