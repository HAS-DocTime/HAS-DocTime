package com.spring.hasdoctime.exceptionHandling.controllerAdvice;

import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.utills.ErrorResponseBody;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Controller advice class for handling exceptions in the application.
 */
@RestControllerAdvice
public class ExceptionController {

    /**
     * Exception handler for DoesNotExistException.
     *
     * @param exception The DoesNotExistException instance.
     * @return ResponseEntity containing the error response body and HTTP status code.
     */
    @ExceptionHandler(value = DoesNotExistException.class)
    public ResponseEntity<ErrorResponseBody> handleException(DoesNotExistException exception){
        ErrorResponseBody responseBody = new ErrorResponseBody(exception.getMessage() + " does not exist");
        return new ResponseEntity<>(responseBody ,HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for MissingParameterException.
     *
     * @param exception The MissingParameterException instance.
     * @return ResponseEntity containing the error response body and HTTP status code.
     */
    @ExceptionHandler(value = MissingParameterException.class)
    public ResponseEntity<ErrorResponseBody> handleException(MissingParameterException exception){
        ErrorResponseBody responseBody = new ErrorResponseBody(exception.getMessage() + " cannot be null");
        return new ResponseEntity<>(responseBody ,HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for SQLIntegrityConstraintViolationException.
     *
     * @param exception The SQLIntegrityConstraintViolationException instance.
     * @return ResponseEntity containing the error response body and HTTP status code.
     */
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponseBody> handleException(SQLIntegrityConstraintViolationException exception){
        ErrorResponseBody responseBody = new ErrorResponseBody(exception.getMessage());
        return new ResponseEntity<>(responseBody ,HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for ConstraintViolationException.
     *
     * @param exception The ConstraintViolationException instance.
     * @return ResponseEntity containing the error response body and HTTP status code.
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseBody> handleException(ConstraintViolationException exception){
        String invalidObjs = "";
        for(ConstraintViolation<?> cv : exception.getConstraintViolations()){
            invalidObjs += ("Enter Valid " + cv.getPropertyPath().toString() + ".");
        }
        ErrorResponseBody responseBody = new ErrorResponseBody(invalidObjs);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for MethodArgumentNotValidException.
     *
     * @param methodArgumentNotValidException The MethodArgumentNotValidException instance.
     * @return ResponseEntity containing the error response body and HTTP status code.
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseBody> handleException(MethodArgumentNotValidException methodArgumentNotValidException){
            String errors = "";
            for(ObjectError error : methodArgumentNotValidException.getBindingResult().getAllErrors()){
                errors += ("Enter Valid " + ((FieldError) error).getField() + ".");
            }
            ErrorResponseBody errorResponseBody = new ErrorResponseBody(errors);
            return new ResponseEntity<>(errorResponseBody, HttpStatus.BAD_REQUEST);
    }
}
