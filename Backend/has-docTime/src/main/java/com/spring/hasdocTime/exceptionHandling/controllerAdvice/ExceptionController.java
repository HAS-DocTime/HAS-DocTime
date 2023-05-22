package com.spring.hasdocTime.exceptionHandling.controllerAdvice;

import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.utills.ErrorResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = DoesNotExistException.class)
    public ResponseEntity<ErrorResponseBody> handleException(DoesNotExistException exception){
        ErrorResponseBody responseBody = new ErrorResponseBody(exception.getMessage() + " does not exist");
        return new ResponseEntity<>(responseBody ,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = MissingParameterException.class)
    public ResponseEntity<ErrorResponseBody> handleException(MissingParameterException exception){
        ErrorResponseBody responseBody = new ErrorResponseBody(exception.getMessage() + " cannot be null");
        return new ResponseEntity<>(responseBody ,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponseBody> handleException(SQLIntegrityConstraintViolationException exception){
        ErrorResponseBody responseBody = new ErrorResponseBody(exception.getMessage());
        return new ResponseEntity<>(responseBody ,HttpStatus.BAD_REQUEST);
    }
}
