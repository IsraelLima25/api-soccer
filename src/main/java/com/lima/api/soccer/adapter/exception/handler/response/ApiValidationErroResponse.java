package com.lima.api.soccer.adapter.exception.handler.response;

public class ApiValidationErroResponse implements ApiSubErrorResponse{

    private final String object;
    private String field;
    private Object rejectedValue;
    private final String message;

    public ApiValidationErroResponse(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public ApiValidationErroResponse(String object, String message, Object rejectedValue, String field) {
        this.object = object;
        this.message = message;
        this.rejectedValue = rejectedValue;
        this.field = field;
    }


    public String getObject() {
        return object;
    }

    public String getMessage() {
        return message;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public String getField() {
        return field;
    }
}
