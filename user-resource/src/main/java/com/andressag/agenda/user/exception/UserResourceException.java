package com.andressag.agenda.user.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResourceException extends RuntimeException {

    private String ipAddress;
    private LocalDateTime errorTimestamp;

    public UserResourceException(Throwable cause) {
        super(cause);
    }
}
