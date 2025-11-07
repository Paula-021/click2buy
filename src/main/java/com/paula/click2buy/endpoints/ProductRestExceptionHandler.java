package com.paula.click2buy.endpoints;


import com.paula.click2buy.exceptions.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handlerException(ProductNotFoundException productNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(404, productNotFoundException.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(404).body(errorResponse);
    }
}
