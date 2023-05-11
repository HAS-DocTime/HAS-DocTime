package com.spring.hasdocTime.exceptionHandling.exception;

public class MissingParameterException extends Exception{
    public MissingParameterException() {
        super();
    }

    public MissingParameterException(String message) {
        super(message);
    }
}
