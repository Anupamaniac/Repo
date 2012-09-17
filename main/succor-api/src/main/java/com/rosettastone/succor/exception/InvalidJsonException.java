package com.rosettastone.succor.exception;

/**
 * The {@code InvalidJsonException} can be throw while parsing of message
 */

public class InvalidJsonException extends ApplicationException {

    public InvalidJsonException(String message) {
        super(message);
    }

    public InvalidJsonException(String message, Throwable cause) {
        super(message, cause);
    }
}
