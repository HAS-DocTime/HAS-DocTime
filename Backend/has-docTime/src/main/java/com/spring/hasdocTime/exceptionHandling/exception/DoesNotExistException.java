package com.spring.hasdocTime.exceptionHandling.exception;

public class DoesNotExistException extends Exception{
    public DoesNotExistException() {
        super();
    }

    public DoesNotExistException(String message) {
        super(message);
    }
}
