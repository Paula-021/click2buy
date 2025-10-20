package com.paula.click2buy.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handlerException(MethodArgumentNotValidException ex) {

        //fazer estrutura do error response (decidir sobre a lista errors)


        final String[] mensagem = {""};
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            mensagem[0] = fieldError.getDefaultMessage();

        });

        ErrorResponse error = new ErrorResponse(
                400,
                mensagem[0],
                System.currentTimeMillis()
        );
        return ResponseEntity.status(400).body(error);

    }
}
