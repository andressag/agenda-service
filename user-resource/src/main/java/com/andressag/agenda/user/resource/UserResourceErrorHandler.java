package com.andressag.agenda.user.resource;

import com.andressag.agenda.model.AgendaErrors;
import com.andressag.agenda.user.exception.FindUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@RestControllerAdvice
public class UserResourceErrorHandler {

    @ExceptionHandler(FindUserException.class)
    public ResponseEntity<UserError> findUserException(FindUserException exception) {
        log.error("Error while querying users, request by IP {}", exception.getIpAddress());
        final UserError error = UserError.builder()
                .errorCode(AgendaErrors.INTERNAL_ERROR.getCode())
                .errorDetails(AgendaErrors.INTERNAL_ERROR.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .body(error);
    }
}
