package com.lima.api.soccer.adapter.exception.handler;

public class BusinessException extends RuntimeException {

    private String message;

    public BusinessException(String message){
        super(message);
    }
}
