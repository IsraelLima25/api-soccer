package com.lima.api.soccer.application.exception;

public class PaymentNotExistsException extends RuntimeException{

    private String message;

    public PaymentNotExistsException(String message){
        super(message);
    }

}
