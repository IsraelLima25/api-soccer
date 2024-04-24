package com.lima.api.soccer.application.exception;

public class BusinessExeption extends RuntimeException {

    private String message;

    public BusinessExeption(String message){
        super(message);
    }
}
