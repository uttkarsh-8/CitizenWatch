package com.policeapi.policerestapis.exception;

import com.policeapi.policerestapis.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.security.core.AuthenticationException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) //used for specific exceptions
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                e.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    //Global Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalExceptions(Exception e, WebRequest webRequest) {
        if (e instanceof AuthenticationException) {

            return handleAuthenticationException((AuthenticationException) e, webRequest);
        }
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                e.getMessage(),
                webRequest.getDescription(false)
        );
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // Default to 500 status
        return new ResponseEntity<>(errorDetails, status);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetails> handleAuthenticationException(AuthenticationException e, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                e.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED); // Return 401 status
    }
}
