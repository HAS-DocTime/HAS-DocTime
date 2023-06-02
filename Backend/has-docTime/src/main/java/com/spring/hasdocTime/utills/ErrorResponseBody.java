package com.spring.hasdocTime.utills;

/**
 * Utility class representing the response body for error messages.
 */
public class ErrorResponseBody {
    private String message;

    /**
     * Constructs an ErrorResponseBody object with the specified error message.
     *
     * @param message The error message.
     */
    public ErrorResponseBody(String message){
        this.message = message;
    }

    /**
     * Retrieves the error message.
     *
     * @return The error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param message The error message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
