package com.paula.click2buy.endpoints;

import com.paula.click2buy.exceptions.CartNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CartRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleException(CartNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(404, e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(404).body(errorResponse);
    }
}
