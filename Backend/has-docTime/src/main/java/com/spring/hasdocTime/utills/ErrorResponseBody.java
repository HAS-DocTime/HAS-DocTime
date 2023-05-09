package com.spring.hasdocTime.utills;

public class ErrorResponseBody {
    private String message;

    public ErrorResponseBody(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
