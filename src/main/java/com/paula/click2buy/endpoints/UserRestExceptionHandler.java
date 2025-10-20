package com.paula.click2buy.endpoints;

import com.paula.click2buy.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handlerException(UserNotFoundException userNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(404, userNotFoundException.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(404).body(errorResponse);
    }


}