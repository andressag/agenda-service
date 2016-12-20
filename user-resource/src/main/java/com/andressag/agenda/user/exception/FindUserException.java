package com.andressag.agenda.user.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FindUserException extends RuntimeException {

    private String ipAddress;
    private LocalDateTime errorTimestamp;

    public FindUserException(Throwable cause) {
        super(cause);
    }
}
