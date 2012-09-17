package com.rosettastone.succor.exception;

/**
 * The {@code ApplicationException} is a common exception for succor project.
 */

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }
}
