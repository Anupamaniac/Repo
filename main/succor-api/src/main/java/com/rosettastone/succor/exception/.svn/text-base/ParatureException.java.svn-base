package com.rosettastone.succor.exception;

/**
 * The {@code ParatureException} can be thrown while interaction with parature
 */

public class ParatureException extends ApplicationException {

    private ParatureErrorCode errorCode;
    /**
     * Requested object's ID. Used for 404 (object not found) error code.
     */
    private String objectId;

    public ParatureException(String message, ParatureErrorCode errorCode, String objectId) {
        super(message);
        this.errorCode = errorCode;
        this.objectId = objectId;
    }

    public ParatureException(String message, ParatureErrorCode code) {
        super(message);
        this.errorCode = code;
    }

    public ParatureErrorCode getErrorCode() {
        return errorCode;
    }

    public String getObjectId() {
        return objectId;
    }
}
