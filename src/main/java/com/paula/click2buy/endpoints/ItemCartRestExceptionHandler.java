package com.paula.click2buy.endpoints;


import com.paula.click2buy.exceptions.ItemCartNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ItemCartRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleException(ItemCartNotFoundException itemCartNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(404, itemCartNotFoundException.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(404).body(errorResponse);
    }

}
