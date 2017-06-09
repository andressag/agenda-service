package com.andressag.agenda.user.exception;

public class CustomerResourceException extends RuntimeException {
    public CustomerResourceException() {
    }

    public CustomerResourceException(String message) {
        super(message);
    }

    public CustomerResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerResourceException(Throwable cause) {
        super(cause);
    }
}
