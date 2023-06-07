package com.spring.hasdocTime.exceptionHandling.exception;

/**
 * Exception class representing a "Does Not Exist" exception.
 */
public class DoesNotExistException extends Exception{

    /**
     * Constructs a new DoesNotExistException with no detail message.
     */
    public DoesNotExistException() {
        super();
    }

    /**
     * Constructs a new DoesNotExistException with the specified detail message.
     *
     * @param message the detail message.
     */
    public DoesNotExistException(String message) {
        super(message);
    }
}
