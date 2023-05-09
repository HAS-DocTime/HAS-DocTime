package com.spring.hasdocTime.exceptionHandling.controllerAdvice;

import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.utills.ErrorResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = DoesNotExistException.class)
    public ResponseEntity<ErrorResponseBody> handleException(DoesNotExistException exception){
        ErrorResponseBody responseBody = new ErrorResponseBody(exception.getMessage() + " does not exist");
        return new ResponseEntity<>(responseBody ,HttpStatus.NOT_FOUND);
    }
}
