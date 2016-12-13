package com.andressag.agenda.user.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.PersistenceException;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@ControllerAdvice
public class UserResourceErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserError> anyException() {
        final UserError error = UserError.builder()
                .errorCode(1000)
                .errorDetails("System Error: Please call support.")
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .body(error);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<UserError> persistenceError(PersistenceException exception) {
        final UserError error = UserError.builder()
                .errorCode(9999)
                .errorDetails(exception.getLocalizedMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .body(error);
    }
}
