package com.spring.hasdocTime.exceptionHandling.exception;

/**
 * Exception class representing a "Missing Parameter" exception.
 */
public class MissingParameterException extends Exception{
    /**
     * Constructs a new MissingParameterException with no detail message.
     */
    public MissingParameterException() {
        super();
    }

    /**
     * Constructs a new MissingParameterException with the specified detail message.
     *
     * @param message the detail message.
     */
    public MissingParameterException(String message) {
        super(message);
    }
}
