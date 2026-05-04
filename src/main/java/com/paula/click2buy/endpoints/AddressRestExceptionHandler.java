package com.paula.click2buy.endpoints;


import com.paula.click2buy.exceptions.AddressNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AddressRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handlerException(AddressNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(404, e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(404).body(errorResponse);
    }
}
