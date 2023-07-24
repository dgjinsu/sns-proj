package com.snsproj.exception;

import com.snsproj.controller.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(SimpleSnsApplicationException.class)
    public ResponseEntity<?> errorHandler(SimpleSnsApplicationException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode().name(), e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> RuntimeExceptionHandler(SimpleSnsApplicationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(null,"INTERNAL_SERVER_ERROR"));
    }
}